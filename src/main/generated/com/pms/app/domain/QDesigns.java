package com.pms.app.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QDesigns is a Querydsl query type for Designs
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QDesigns extends EntityPathBase<Designs> {

    private static final long serialVersionUID = 656070781L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDesigns designs = new QDesigns("designs");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.util.Date> created = _super.created;

    public final QCustomers customer;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> lastModified = _super.lastModified;

    public final StringPath name = createString("name");

    public final QDesigns parent;

    //inherited
    public final NumberPath<Long> version = _super.version;

    public QDesigns(String variable) {
        this(Designs.class, forVariable(variable), INITS);
    }

    public QDesigns(Path<? extends Designs> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QDesigns(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QDesigns(PathMetadata<?> metadata, PathInits inits) {
        this(Designs.class, metadata, inits);
    }

    public QDesigns(Class<? extends Designs> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new QCustomers(forProperty("customer"), inits.get("customer")) : null;
        this.parent = inits.isInitialized("parent") ? new QDesigns(forProperty("parent"), inits.get("parent")) : null;
    }

}

