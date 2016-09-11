package com.pms.app.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QColors is a Querydsl query type for Colors
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QColors extends EntityPathBase<Colors> {

    private static final long serialVersionUID = 140114536L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QColors colors = new QColors("colors");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    public final StringPath code = createString("code");

    //inherited
    public final DateTimePath<java.util.Date> created = _super.created;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> lastModified = _super.lastModified;

    public final StringPath name = createString("name");

    //inherited
    public final NumberPath<Long> version = _super.version;

    public final QYarns yarn;

    public QColors(String variable) {
        this(Colors.class, forVariable(variable), INITS);
    }

    public QColors(Path<? extends Colors> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QColors(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QColors(PathMetadata<?> metadata, PathInits inits) {
        this(Colors.class, metadata, inits);
    }

    public QColors(Class<? extends Colors> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.yarn = inits.isInitialized("yarn") ? new QYarns(forProperty("yarn")) : null;
    }

}

