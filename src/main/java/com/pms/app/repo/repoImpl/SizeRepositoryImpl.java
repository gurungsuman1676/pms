package com.pms.app.repo.repoImpl;

import com.mysema.query.support.Expressions;
import com.mysema.query.types.expr.StringExpression;
import com.pms.app.domain.QSizes;
import com.pms.app.domain.Sizes;
import com.pms.app.repo.SizeRepository;
import com.pms.app.repo.repoCustom.SizeRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * Created by arjun on 7/11/2017.
 */
public class SizeRepositoryImpl extends AbstractRepositoryImpl<Sizes, SizeRepository> implements SizeRepositoryCustom {

    public SizeRepositoryImpl() {
        super(Sizes.class);
    }

    @Lazy
    @Autowired
    public void setRepository(SizeRepository repository) {
        this.repository = repository;
    }


    @Override
    public Long findIdByName(String sizeName) {
        QSizes sizes = QSizes.sizes;
        StringExpression nameExpression = Expressions.stringTemplate("replace({0},' ','')", sizes.name);
        StringExpression providedExpression = Expressions.stringTemplate("replace({0},' ','')", sizeName);


        List<Long> ids = from(sizes)
                .where(nameExpression.equalsIgnoreCase(providedExpression))
                .list(sizes.id);
        return ids != null && ids.size() > 0 ? ids.get(0) : null;
    }
}
