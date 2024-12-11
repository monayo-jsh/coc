package open.api.coc.clans.database.entity.clan;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClanAssignedPlayerEntity is a Querydsl query type for ClanAssignedPlayerEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClanAssignedPlayerEntity extends EntityPathBase<ClanAssignedPlayerEntity> {

    private static final long serialVersionUID = 1419152200L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClanAssignedPlayerEntity clanAssignedPlayerEntity = new QClanAssignedPlayerEntity("clanAssignedPlayerEntity");

    public final QClanEntity clan;

    public final QClanAssignedPlayerPKEntity id;

    public QClanAssignedPlayerEntity(String variable) {
        this(ClanAssignedPlayerEntity.class, forVariable(variable), INITS);
    }

    public QClanAssignedPlayerEntity(Path<? extends ClanAssignedPlayerEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClanAssignedPlayerEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClanAssignedPlayerEntity(PathMetadata metadata, PathInits inits) {
        this(ClanAssignedPlayerEntity.class, metadata, inits);
    }

    public QClanAssignedPlayerEntity(Class<? extends ClanAssignedPlayerEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clan = inits.isInitialized("clan") ? new QClanEntity(forProperty("clan"), inits.get("clan")) : null;
        this.id = inits.isInitialized("id") ? new QClanAssignedPlayerPKEntity(forProperty("id")) : null;
    }

}

