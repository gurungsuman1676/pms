package com.pms.app.repo.repoImpl;

import com.mysema.query.support.Expressions;
import com.mysema.query.types.expr.StringExpression;
import com.pms.app.domain.Designs;
import com.pms.app.domain.QDesigns;
import com.pms.app.repo.DesignRepository;
import com.pms.app.repo.repoCustom.DesignRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * Created by arjun on 7/11/2017.
 */
public class DesignRepositoryImpl extends AbstractRepositoryImpl<Designs, DesignRepository> implements DesignRepositoryCustom {
    public DesignRepositoryImpl() {
        super(Designs.class);
    }

    @Lazy
    @Autowired
    public void setRepository(DesignRepository designRepository) {
        this.repository = designRepository;
    }

    @Override
    public Long findIdByNameAndCustomer(String name, Long customerId) {
        QDesigns designs = QDesigns.designs;
        StringExpression nameExpression = Expressions.stringTemplate("replace({0},' ','')", designs.name);
        StringExpression providedExpression = Expressions.stringTemplate("replace({0},' ','')", name);


        List<Long> ids = from(designs)
                .where(nameExpression.equalsIgnoreCase(providedExpression).and(designs.customer.id.eq(customerId)))
                .list(designs.id);
        return ids != null && ids.size() > 0 ? ids.get(0) : null;
    }
}
