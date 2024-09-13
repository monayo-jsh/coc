package open.api.coc.clans.database.entity.raid;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRaiderEntity is a Querydsl query type for RaiderEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRaiderEntity extends EntityPathBase<RaiderEntity> {

    private static final long serialVersionUID = 1629238406L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRaiderEntity raiderEntity = new QRaiderEntity("raiderEntity");

    public final NumberPath<Integer> attacks = createNumber("attacks", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QRaidEntity raid;

    public final NumberPath<Integer> resourceLooted = createNumber("resourceLooted", Integer.class);

    public final StringPath tag = createString("tag");

    public QRaiderEntity(String variable) {
        this(RaiderEntity.class, forVariable(variable), INITS);
    }

    public QRaiderEntity(Path<? extends RaiderEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRaiderEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRaiderEntity(PathMetadata metadata, PathInits inits) {
        this(RaiderEntity.class, metadata, inits);
    }

    public QRaiderEntity(Class<? extends RaiderEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.raid = inits.isInitialized("raid") ? new QRaidEntity(forProperty("raid")) : null;
    }

}

