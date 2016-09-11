package com.pms.app.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPrices is a Querydsl query type for Prices
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPrices extends EntityPathBase<Prices> {

    private static final long serialVersionUID = 514962754L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPrices prices = new QPrices("prices");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    public final NumberPath<Double> amount = createNumber("amount", Double.class);

    //inherited
    public final DateTimePath<java.util.Date> created = _super.created;

    public final QDesigns design;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> lastModified = _super.lastModified;

    public final QSizes size;

    //inherited
    public final NumberPath<Long> version = _super.version;

    public final QYarns yarn;

    public QPrices(String variable) {
        this(Prices.class, forVariable(variable), INITS);
    }

    public QPrices(Path<? extends Prices> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPrices(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPrices(PathMetadata<?> metadata, PathInits inits) {
        this(Prices.class, metadata, inits);
    }

    public QPrices(Class<? extends Prices> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.design = inits.isInitialized("design") ? new QDesigns(forProperty("design"), inits.get("design")) : null;
        this.size = inits.isInitialized("size") ? new QSizes(forProperty("size")) : null;
        this.yarn = inits.isInitialized("yarn") ? new QYarns(forProperty("yarn")) : null;
    }

}

