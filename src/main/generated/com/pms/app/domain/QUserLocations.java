package com.pms.app.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QUserLocations is a Querydsl query type for UserLocations
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUserLocations extends EntityPathBase<UserLocations> {

    private static final long serialVersionUID = -656319525L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserLocations userLocations = new QUserLocations("userLocations");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.util.Date> created = _super.created;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> lastModified = _super.lastModified;

    public final QLocations location;

    public final QUsers user;

    //inherited
    public final NumberPath<Long> version = _super.version;

    public QUserLocations(String variable) {
        this(UserLocations.class, forVariable(variable), INITS);
    }

    public QUserLocations(Path<? extends UserLocations> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUserLocations(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUserLocations(PathMetadata<?> metadata, PathInits inits) {
        this(UserLocations.class, metadata, inits);
    }

    public QUserLocations(Class<? extends UserLocations> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.location = inits.isInitialized("location") ? new QLocations(forProperty("location")) : null;
        this.user = inits.isInitialized("user") ? new QUsers(forProperty("user")) : null;
    }

}

