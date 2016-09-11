package com.pms.app.schema;

import com.mysema.query.types.expr.*;

import com.mysema.query.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.pms.app.schema.QClothOrderResource is a Querydsl Projection type for ClothOrderResource
 */
@Generated("com.mysema.query.codegen.ProjectionSerializer")
public class QClothOrderResource extends ConstructorExpression<ClothOrderResource> {

    private static final long serialVersionUID = 768558717L;

    public QClothOrderResource(com.mysema.query.types.Expression<String> designName, com.mysema.query.types.Expression<String> sizeName, com.mysema.query.types.Expression<String> color, com.mysema.query.types.Expression<String> colorCode, com.mysema.query.types.Expression<Long> clothCount, com.mysema.query.types.Expression<String> print, com.mysema.query.types.Expression<? extends java.util.Date> orderDate, com.mysema.query.types.Expression<? extends java.util.Date> deliveryDate, com.mysema.query.types.Expression<String> customerName, com.mysema.query.types.Expression<Integer> orderNo) {
        super(ClothOrderResource.class, new Class[]{String.class, String.class, String.class, String.class, long.class, String.class, java.util.Date.class, java.util.Date.class, String.class, int.class}, designName, sizeName, color, colorCode, clothCount, print, orderDate, deliveryDate, customerName, orderNo);
    }

}

