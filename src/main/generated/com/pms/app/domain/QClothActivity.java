package com.pms.app.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QClothActivity is a Querydsl query type for ClothActivity
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QClothActivity extends EntityPathBase<ClothActivity> {

    private static final long serialVersionUID = 1143879505L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClothActivity clothActivity = new QClothActivity("clothActivity");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    public final QClothes cloth;

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

    public QClothActivity(String variable) {
        this(ClothActivity.class, forVariable(variable), INITS);
    }

    public QClothActivity(Path<? extends ClothActivity> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QClothActivity(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QClothActivity(PathMetadata<?> metadata, PathInits inits) {
        this(ClothActivity.class, metadata, inits);
    }

    public QClothActivity(Class<? extends ClothActivity> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cloth = inits.isInitialized("cloth") ? new QClothes(forProperty("cloth"), inits.get("cloth")) : null;
        this.location = inits.isInitialized("location") ? new QLocations(forProperty("location")) : null;
        this.user = inits.isInitialized("user") ? new QUsers(forProperty("user")) : null;
    }

}

