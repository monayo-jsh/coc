package open.api.coc.clans.database.entity.player;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayerSpellEntity is a Querydsl query type for PlayerSpellEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayerSpellEntity extends EntityPathBase<PlayerSpellEntity> {

    private static final long serialVersionUID = 349724309L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayerSpellEntity playerSpellEntity = new QPlayerSpellEntity("playerSpellEntity");

    public final open.api.coc.clans.database.entity.player.common.QPlayerItemPKEntity id;

    public final open.api.coc.clans.database.entity.player.common.QPlayerItemEntity levelInfo;

    public final QPlayerEntity player;

    public final EnumPath<open.api.coc.clans.database.entity.player.common.SpellType> type = createEnum("type", open.api.coc.clans.database.entity.player.common.SpellType.class);

    public QPlayerSpellEntity(String variable) {
        this(PlayerSpellEntity.class, forVariable(variable), INITS);
    }

    public QPlayerSpellEntity(Path<? extends PlayerSpellEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayerSpellEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayerSpellEntity(PathMetadata metadata, PathInits inits) {
        this(PlayerSpellEntity.class, metadata, inits);
    }

    public QPlayerSpellEntity(Class<? extends PlayerSpellEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new open.api.coc.clans.database.entity.player.common.QPlayerItemPKEntity(forProperty("id")) : null;
        this.levelInfo = inits.isInitialized("levelInfo") ? new open.api.coc.clans.database.entity.player.common.QPlayerItemEntity(forProperty("levelInfo")) : null;
        this.player = inits.isInitialized("player") ? new QPlayerEntity(forProperty("player"), inits.get("player")) : null;
    }

}

