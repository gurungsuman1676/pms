package com.pms.app.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSizes is a Querydsl query type for Sizes
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QSizes extends EntityPathBase<Sizes> {

    private static final long serialVersionUID = -1089248102L;

    public static final QSizes sizes = new QSizes("sizes");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.util.Date> created = _super.created;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> lastModified = _super.lastModified;

    public final StringPath name = createString("name");

    //inherited
    public final NumberPath<Long> version = _super.version;

    public QSizes(String variable) {
        super(Sizes.class, forVariable(variable));
    }

    public QSizes(Path<? extends Sizes> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSizes(PathMetadata<?> metadata) {
        super(Sizes.class, metadata);
    }

}

