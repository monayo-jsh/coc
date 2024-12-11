package open.api.coc.clans.database.entity.player;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayerTroopsEntity is a Querydsl query type for PlayerTroopsEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayerTroopsEntity extends EntityPathBase<PlayerTroopsEntity> {

    private static final long serialVersionUID = -638113670L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayerTroopsEntity playerTroopsEntity = new QPlayerTroopsEntity("playerTroopsEntity");

    public final open.api.coc.clans.database.entity.player.common.QPlayerItemPKEntity id;

    public final open.api.coc.clans.database.entity.player.common.QPlayerItemEntity levelInfo;

    public final QPlayerEntity player;

    public final EnumPath<open.api.coc.clans.database.entity.player.common.TroopType> type = createEnum("type", open.api.coc.clans.database.entity.player.common.TroopType.class);

    public QPlayerTroopsEntity(String variable) {
        this(PlayerTroopsEntity.class, forVariable(variable), INITS);
    }

    public QPlayerTroopsEntity(Path<? extends PlayerTroopsEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayerTroopsEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayerTroopsEntity(PathMetadata metadata, PathInits inits) {
        this(PlayerTroopsEntity.class, metadata, inits);
    }

    public QPlayerTroopsEntity(Class<? extends PlayerTroopsEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new open.api.coc.clans.database.entity.player.common.QPlayerItemPKEntity(forProperty("id")) : null;
        this.levelInfo = inits.isInitialized("levelInfo") ? new open.api.coc.clans.database.entity.player.common.QPlayerItemEntity(forProperty("levelInfo")) : null;
        this.player = inits.isInitialized("player") ? new QPlayerEntity(forProperty("player"), inits.get("player")) : null;
    }

}

