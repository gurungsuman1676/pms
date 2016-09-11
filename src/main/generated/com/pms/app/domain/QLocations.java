package com.pms.app.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLocations is a Querydsl query type for Locations
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QLocations extends EntityPathBase<Locations> {

    private static final long serialVersionUID = 559961958L;

    public static final QLocations locations = new QLocations("locations");

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

    public QLocations(String variable) {
        super(Locations.class, forVariable(variable));
    }

    public QLocations(Path<? extends Locations> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocations(PathMetadata<?> metadata) {
        super(Locations.class, metadata);
    }

}

