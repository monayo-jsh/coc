package open.api.coc.clans.database.entity.player;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayerHeroEntity is a Querydsl query type for PlayerHeroEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayerHeroEntity extends EntityPathBase<PlayerHeroEntity> {

    private static final long serialVersionUID = 1821776851L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayerHeroEntity playerHeroEntity = new QPlayerHeroEntity("playerHeroEntity");

    public final open.api.coc.clans.database.entity.player.common.QPlayerItemPKEntity id;

    public final open.api.coc.clans.database.entity.player.common.QPlayerItemEntity levelInfo;

    public final QPlayerEntity player;

    public QPlayerHeroEntity(String variable) {
        this(PlayerHeroEntity.class, forVariable(variable), INITS);
    }

    public QPlayerHeroEntity(Path<? extends PlayerHeroEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayerHeroEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayerHeroEntity(PathMetadata metadata, PathInits inits) {
        this(PlayerHeroEntity.class, metadata, inits);
    }

    public QPlayerHeroEntity(Class<? extends PlayerHeroEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new open.api.coc.clans.database.entity.player.common.QPlayerItemPKEntity(forProperty("id")) : null;
        this.levelInfo = inits.isInitialized("levelInfo") ? new open.api.coc.clans.database.entity.player.common.QPlayerItemEntity(forProperty("levelInfo")) : null;
        this.player = inits.isInitialized("player") ? new QPlayerEntity(forProperty("player"), inits.get("player")) : null;
    }

}

