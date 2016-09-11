package com.pms.app.schema;

import com.mysema.query.types.expr.*;

import com.mysema.query.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.pms.app.schema.QActivityResource is a Querydsl Projection type for ActivityResource
 */
@Generated("com.mysema.query.codegen.ProjectionSerializer")
public class QActivityResource extends ConstructorExpression<ActivityResource> {

    private static final long serialVersionUID = 661510136L;

    public QActivityResource(com.mysema.query.types.Expression<Long> id, com.mysema.query.types.Expression<String> location, com.mysema.query.types.Expression<? extends java.util.Date> created, com.mysema.query.types.Expression<String> username) {
        super(ActivityResource.class, new Class[]{long.class, String.class, java.util.Date.class, String.class}, id, location, created, username);
    }

}

