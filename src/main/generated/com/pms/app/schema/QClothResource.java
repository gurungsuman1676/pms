package com.pms.app.schema;

import com.mysema.query.types.expr.*;

import com.mysema.query.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.pms.app.schema.QClothResource is a Querydsl Projection type for ClothResource
 */
@Generated("com.mysema.query.codegen.ProjectionSerializer")
public class QClothResource extends ConstructorExpression<ClothResource> {

    private static final long serialVersionUID = -486661523L;

    public QClothResource(com.mysema.query.types.Expression<Long> id, com.mysema.query.types.Expression<String> locationName, com.mysema.query.types.Expression<Double> price, com.mysema.query.types.Expression<String> currency, com.mysema.query.types.Expression<Integer> order_no, com.mysema.query.types.Expression<String> designName, com.mysema.query.types.Expression<String> yarnName, com.mysema.query.types.Expression<String> colorCode, com.mysema.query.types.Expression<String> size, com.mysema.query.types.Expression<String> customerName, com.mysema.query.types.Expression<? extends java.util.Date> delivery_date, com.mysema.query.types.Expression<String> printCurrency, com.mysema.query.types.Expression<Double> printAmount, com.mysema.query.types.Expression<String> printName, com.mysema.query.types.Expression<String> shippingNumber, com.mysema.query.types.Expression<String> boxNumber, com.mysema.query.types.Expression<String> weight, com.mysema.query.types.Expression<Boolean> isReturn, com.mysema.query.types.Expression<? extends java.util.Date> created) {
        super(ClothResource.class, new Class[]{long.class, String.class, double.class, String.class, int.class, String.class, String.class, String.class, String.class, String.class, java.util.Date.class, String.class, double.class, String.class, String.class, String.class, String.class, boolean.class, java.util.Date.class}, id, locationName, price, currency, order_no, designName, yarnName, colorCode, size, customerName, delivery_date, printCurrency, printAmount, printName, shippingNumber, boxNumber, weight, isReturn, created);
    }

}

