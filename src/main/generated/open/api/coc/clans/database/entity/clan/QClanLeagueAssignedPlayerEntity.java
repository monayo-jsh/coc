package open.api.coc.clans.database.entity.clan;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClanLeagueAssignedPlayerEntity is a Querydsl query type for ClanLeagueAssignedPlayerEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClanLeagueAssignedPlayerEntity extends EntityPathBase<ClanLeagueAssignedPlayerEntity> {

    private static final long serialVersionUID = 1901634455L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClanLeagueAssignedPlayerEntity clanLeagueAssignedPlayerEntity = new QClanLeagueAssignedPlayerEntity("clanLeagueAssignedPlayerEntity");

    public final QClanEntity clan;

    public final QClanAssignedPlayerPKEntity id;

    public QClanLeagueAssignedPlayerEntity(String variable) {
        this(ClanLeagueAssignedPlayerEntity.class, forVariable(variable), INITS);
    }

    public QClanLeagueAssignedPlayerEntity(Path<? extends ClanLeagueAssignedPlayerEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClanLeagueAssignedPlayerEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClanLeagueAssignedPlayerEntity(PathMetadata metadata, PathInits inits) {
        this(ClanLeagueAssignedPlayerEntity.class, metadata, inits);
    }

    public QClanLeagueAssignedPlayerEntity(Class<? extends ClanLeagueAssignedPlayerEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clan = inits.isInitialized("clan") ? new QClanEntity(forProperty("clan"), inits.get("clan")) : null;
        this.id = inits.isInitialized("id") ? new QClanAssignedPlayerPKEntity(forProperty("id")) : null;
    }

}

