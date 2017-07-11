package com.pms.app.repo.repoImpl;

import com.mysema.query.support.Expressions;
import com.mysema.query.types.expr.StringExpression;
import com.pms.app.domain.Prints;
import com.pms.app.domain.QPrints;
import com.pms.app.repo.PrintRepository;
import com.pms.app.repo.repoCustom.PrintRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * Created by arjun on 7/11/2017.
 */
public class PrintRepositoryImpl extends AbstractRepositoryImpl<Prints, PrintRepository> implements PrintRepositoryCustom {
    public PrintRepositoryImpl() {
        super(Prints.class);
    }

    @Lazy
    @Autowired
    public void setRepository(PrintRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Long> findByNameAndSizeId(String printName, Long sizeId) {
        QPrints prints = QPrints.prints;
        StringExpression nameExpression = Expressions.stringTemplate("replace({0},' ','')", prints.name);
        StringExpression providedExpression = Expressions.stringTemplate("replace({0},' ','')", printName);
        return from(prints)
                .where(nameExpression.equalsIgnoreCase(providedExpression).and(prints.size.id.eq(sizeId)))
                .list(prints.id);
    }
}
