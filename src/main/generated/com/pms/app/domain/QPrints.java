package com.pms.app.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPrints is a Querydsl query type for Prints
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPrints extends EntityPathBase<Prints> {

    private static final long serialVersionUID = 514973790L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPrints prints = new QPrints("prints");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    public final NumberPath<Double> amount = createNumber("amount", Double.class);

    //inherited
    public final DateTimePath<java.util.Date> created = _super.created;

    public final QCurrency currency;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> lastModified = _super.lastModified;

    public final StringPath name = createString("name");

    public final QSizes size;

    //inherited
    public final NumberPath<Long> version = _super.version;

    public QPrints(String variable) {
        this(Prints.class, forVariable(variable), INITS);
    }

    public QPrints(Path<? extends Prints> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPrints(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPrints(PathMetadata<?> metadata, PathInits inits) {
        this(Prints.class, metadata, inits);
    }

    public QPrints(Class<? extends Prints> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.currency = inits.isInitialized("currency") ? new QCurrency(forProperty("currency")) : null;
        this.size = inits.isInitialized("size") ? new QSizes(forProperty("size")) : null;
    }

}

