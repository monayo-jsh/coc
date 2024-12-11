package open.api.coc.clans.database.entity.player;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayerHeroEquipmentEntity is a Querydsl query type for PlayerHeroEquipmentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayerHeroEquipmentEntity extends EntityPathBase<PlayerHeroEquipmentEntity> {

    private static final long serialVersionUID = -691042015L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayerHeroEquipmentEntity playerHeroEquipmentEntity = new QPlayerHeroEquipmentEntity("playerHeroEquipmentEntity");

    public final open.api.coc.clans.database.entity.player.common.QPlayerItemPKEntity id;

    public final open.api.coc.clans.database.entity.player.common.QPlayerItemEntity levelInfo;

    public final QPlayerEntity player;

    public final StringPath targetHeroName = createString("targetHeroName");

    public final EnumPath<open.api.coc.clans.database.entity.common.YnType> wearYn = createEnum("wearYn", open.api.coc.clans.database.entity.common.YnType.class);

    public QPlayerHeroEquipmentEntity(String variable) {
        this(PlayerHeroEquipmentEntity.class, forVariable(variable), INITS);
    }

    public QPlayerHeroEquipmentEntity(Path<? extends PlayerHeroEquipmentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayerHeroEquipmentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayerHeroEquipmentEntity(PathMetadata metadata, PathInits inits) {
        this(PlayerHeroEquipmentEntity.class, metadata, inits);
    }

    public QPlayerHeroEquipmentEntity(Class<? extends PlayerHeroEquipmentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new open.api.coc.clans.database.entity.player.common.QPlayerItemPKEntity(forProperty("id")) : null;
        this.levelInfo = inits.isInitialized("levelInfo") ? new open.api.coc.clans.database.entity.player.common.QPlayerItemEntity(forProperty("levelInfo")) : null;
        this.player = inits.isInitialized("player") ? new QPlayerEntity(forProperty("player"), inits.get("player")) : null;
    }

}

