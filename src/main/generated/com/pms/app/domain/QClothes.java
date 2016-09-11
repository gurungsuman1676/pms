package com.pms.app.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QClothes is a Querydsl query type for Clothes
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QClothes extends EntityPathBase<Clothes> {

    private static final long serialVersionUID = -34394544L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClothes clothes = new QClothes("clothes");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    public final StringPath boxNumber = createString("boxNumber");

    public final QColors color;

    //inherited
    public final DateTimePath<java.util.Date> created = _super.created;

    public final QCustomers customer;

    public final DateTimePath<java.util.Date> deliver_date = createDateTime("deliver_date", java.util.Date.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath isReturn = createBoolean("isReturn");

    //inherited
    public final DateTimePath<java.util.Date> lastModified = _super.lastModified;

    public final QLocations location;

    public final NumberPath<Integer> order_no = createNumber("order_no", Integer.class);

    public final QPrices price;

    public final QPrints print;

    public final StringPath shipping = createString("shipping");

    public final StringPath status = createString("status");

    //inherited
    public final NumberPath<Long> version = _super.version;

    public final StringPath weight = createString("weight");

    public QClothes(String variable) {
        this(Clothes.class, forVariable(variable), INITS);
    }

    public QClothes(Path<? extends Clothes> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QClothes(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QClothes(PathMetadata<?> metadata, PathInits inits) {
        this(Clothes.class, metadata, inits);
    }

    public QClothes(Class<? extends Clothes> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.color = inits.isInitialized("color") ? new QColors(forProperty("color"), inits.get("color")) : null;
        this.customer = inits.isInitialized("customer") ? new QCustomers(forProperty("customer"), inits.get("customer")) : null;
        this.location = inits.isInitialized("location") ? new QLocations(forProperty("location")) : null;
        this.price = inits.isInitialized("price") ? new QPrices(forProperty("price"), inits.get("price")) : null;
        this.print = inits.isInitialized("print") ? new QPrints(forProperty("print"), inits.get("print")) : null;
    }

}

