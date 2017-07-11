package com.pms.app.repo.repoImpl;

import com.mysema.query.support.Expressions;
import com.mysema.query.types.expr.StringExpression;
import com.pms.app.domain.Colors;
import com.pms.app.domain.QColors;
import com.pms.app.repo.ColorRepository;
import com.pms.app.repo.repoCustom.ColorRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * Created by arjun on 7/11/2017.
 */
public class ColorRepositoryImpl extends AbstractRepositoryImpl<Colors, ColorRepository> implements ColorRepositoryCustom {

    public ColorRepositoryImpl() {
        super(Colors.class);
    }

    @Lazy
    @Autowired
    public void setRepository(ColorRepository repository) {
        this.repository = repository;
    }


    @Override
    public Colors findByCodeForImport(String colorCode) {
        QColors colors = QColors.colors;
        StringExpression nameExpression = Expressions.stringTemplate("replace({0},' ','')", colors.code);
        StringExpression providedExpression = Expressions.stringTemplate("replace({0},' ','')", colorCode);


        List<Colors> colorsList = from(colors)
                .where(nameExpression.equalsIgnoreCase(providedExpression))
                .list(colors);
        return colorsList != null && colorsList.size() > 0 ? colorsList.get(0) : null;
    }
}
