package com.pms.app.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QCustomers is a Querydsl query type for Customers
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QCustomers extends EntityPathBase<Customers> {

    private static final long serialVersionUID = -926253987L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomers customers = new QCustomers("customers");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.util.Date> created = _super.created;

    public final QCurrency currency;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> lastModified = _super.lastModified;

    public final StringPath name = createString("name");

    public final QCustomers parent;

    //inherited
    public final NumberPath<Long> version = _super.version;

    public QCustomers(String variable) {
        this(Customers.class, forVariable(variable), INITS);
    }

    public QCustomers(Path<? extends Customers> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QCustomers(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QCustomers(PathMetadata<?> metadata, PathInits inits) {
        this(Customers.class, metadata, inits);
    }

    public QCustomers(Class<? extends Customers> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.currency = inits.isInitialized("currency") ? new QCurrency(forProperty("currency")) : null;
        this.parent = inits.isInitialized("parent") ? new QCustomers(forProperty("parent"), inits.get("parent")) : null;
    }

}

