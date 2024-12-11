package open.api.coc.clans.database.entity.league;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLeagueEntity is a Querydsl query type for LeagueEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLeagueEntity extends EntityPathBase<LeagueEntity> {

    private static final long serialVersionUID = -374834567L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLeagueEntity leagueEntity = new QLeagueEntity("leagueEntity");

    public final open.api.coc.clans.database.entity.common.QIconUrlEntity iconUrl;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final ListPath<open.api.coc.clans.database.entity.player.PlayerEntity, open.api.coc.clans.database.entity.player.QPlayerEntity> players = this.<open.api.coc.clans.database.entity.player.PlayerEntity, open.api.coc.clans.database.entity.player.QPlayerEntity>createList("players", open.api.coc.clans.database.entity.player.PlayerEntity.class, open.api.coc.clans.database.entity.player.QPlayerEntity.class, PathInits.DIRECT2);

    public QLeagueEntity(String variable) {
        this(LeagueEntity.class, forVariable(variable), INITS);
    }

    public QLeagueEntity(Path<? extends LeagueEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLeagueEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLeagueEntity(PathMetadata metadata, PathInits inits) {
        this(LeagueEntity.class, metadata, inits);
    }

    public QLeagueEntity(Class<? extends LeagueEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.iconUrl = inits.isInitialized("iconUrl") ? new open.api.coc.clans.database.entity.common.QIconUrlEntity(forProperty("iconUrl")) : null;
    }

}

