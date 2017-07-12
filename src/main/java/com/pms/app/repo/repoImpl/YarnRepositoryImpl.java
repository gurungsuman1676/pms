package com.pms.app.repo.repoImpl;

import com.mysema.query.support.Expressions;
import com.mysema.query.types.expr.StringExpression;
import com.pms.app.domain.QYarns;
import com.pms.app.domain.Yarns;
import com.pms.app.repo.YarnRepository;
import com.pms.app.repo.repoCustom.YarnRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * Created by arjun on 7/11/2017.
 */
public class YarnRepositoryImpl extends AbstractRepositoryImpl<Yarns, YarnRepository> implements YarnRepositoryCustom {
    public YarnRepositoryImpl() {
        super(Yarns.class);
    }

    @Lazy
    @Autowired
    public void setRepository(YarnRepository repository) {
        this.repository = repository;
    }

    @Override
    public Yarns findByNameForImport(String yarnName) {
        QYarns yarns = QYarns.yarns;
        StringExpression nameExpression = Expressions.stringTemplate("replace({0},' ','')", yarns.name);
        StringExpression providedExpression = Expressions.stringTemplate("replace({0},' ','')", yarnName);
        List<Yarns> yarnsList = from(yarns)
                .where(nameExpression.equalsIgnoreCase(providedExpression))
                .list(yarns);


        return yarnsList != null && yarnsList.size() > 0 ? yarnsList.get(0) : null;
    }

}