package com.pms.app.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QYarns is a Querydsl query type for Yarns
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QYarns extends EntityPathBase<Yarns> {

    private static final long serialVersionUID = -1083952713L;

    public static final QYarns yarns = new QYarns("yarns");

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

    public QYarns(String variable) {
        super(Yarns.class, forVariable(variable));
    }

    public QYarns(Path<? extends Yarns> path) {
        super(path.getType(), path.getMetadata());
    }

    public QYarns(PathMetadata<?> metadata) {
        super(Yarns.class, metadata);
    }

}

