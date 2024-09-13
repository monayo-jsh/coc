package open.api.coc.clans.database.entity.clan;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClanBadgeEntity is a Querydsl query type for ClanBadgeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClanBadgeEntity extends EntityPathBase<ClanBadgeEntity> {

    private static final long serialVersionUID = -1838268304L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClanBadgeEntity clanBadgeEntity = new QClanBadgeEntity("clanBadgeEntity");

    public final open.api.coc.clans.database.entity.common.QIconUrlEntity iconUrl;

    public final StringPath tag = createString("tag");

    public QClanBadgeEntity(String variable) {
        this(ClanBadgeEntity.class, forVariable(variable), INITS);
    }

    public QClanBadgeEntity(Path<? extends ClanBadgeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClanBadgeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClanBadgeEntity(PathMetadata metadata, PathInits inits) {
        this(ClanBadgeEntity.class, metadata, inits);
    }

    public QClanBadgeEntity(Class<? extends ClanBadgeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.iconUrl = inits.isInitialized("iconUrl") ? new open.api.coc.clans.database.entity.common.QIconUrlEntity(forProperty("iconUrl")) : null;
    }

}

