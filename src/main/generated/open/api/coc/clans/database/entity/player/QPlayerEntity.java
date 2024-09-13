package open.api.coc.clans.database.entity.player;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayerEntity is a Querydsl query type for PlayerEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayerEntity extends EntityPathBase<PlayerEntity> {

    private static final long serialVersionUID = 110288441L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayerEntity playerEntity = new QPlayerEntity("playerEntity");

    public final open.api.coc.clans.database.entity.common.QBaseEntity _super = new open.api.coc.clans.database.entity.common.QBaseEntity(this);

    public final NumberPath<Integer> attackWins = createNumber("attackWins", Integer.class);

    public final NumberPath<Integer> bestTrophies = createNumber("bestTrophies", Integer.class);

    public final open.api.coc.clans.database.entity.clan.QClanEntity clan;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Integer> defenseWins = createNumber("defenseWins", Integer.class);

    public final NumberPath<Integer> donations = createNumber("donations", Integer.class);

    public final NumberPath<Integer> donationsReceived = createNumber("donationsReceived", Integer.class);

    public final NumberPath<Integer> expLevel = createNumber("expLevel", Integer.class);

    public final ListPath<PlayerHeroEquipmentEntity, QPlayerHeroEquipmentEntity> heroEquipments = this.<PlayerHeroEquipmentEntity, QPlayerHeroEquipmentEntity>createList("heroEquipments", PlayerHeroEquipmentEntity.class, QPlayerHeroEquipmentEntity.class, PathInits.DIRECT2);

    public final ListPath<PlayerHeroEntity, QPlayerHeroEntity> heroes = this.<PlayerHeroEntity, QPlayerHeroEntity>createList("heroes", PlayerHeroEntity.class, QPlayerHeroEntity.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final open.api.coc.clans.database.entity.league.QLeagueEntity league;

    public final StringPath name = createString("name");

    public final StringPath playerTag = createString("playerTag");

    public final StringPath role = createString("role");

    public final ListPath<PlayerSpellEntity, QPlayerSpellEntity> spells = this.<PlayerSpellEntity, QPlayerSpellEntity>createList("spells", PlayerSpellEntity.class, QPlayerSpellEntity.class, PathInits.DIRECT2);

    public final EnumPath<open.api.coc.clans.database.entity.common.YnType> supportYn = createEnum("supportYn", open.api.coc.clans.database.entity.common.YnType.class);

    public final NumberPath<Integer> townHallLevel = createNumber("townHallLevel", Integer.class);

    public final ListPath<PlayerTroopsEntity, QPlayerTroopsEntity> troops = this.<PlayerTroopsEntity, QPlayerTroopsEntity>createList("troops", PlayerTroopsEntity.class, QPlayerTroopsEntity.class, PathInits.DIRECT2);

    public final NumberPath<Integer> trophies = createNumber("trophies", Integer.class);

    public final EnumPath<open.api.coc.clans.database.entity.player.common.WarPreferenceType> warPreference = createEnum("warPreference", open.api.coc.clans.database.entity.player.common.WarPreferenceType.class);

    public final NumberPath<Integer> warStars = createNumber("warStars", Integer.class);

    public QPlayerEntity(String variable) {
        this(PlayerEntity.class, forVariable(variable), INITS);
    }

    public QPlayerEntity(Path<? extends PlayerEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayerEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayerEntity(PathMetadata metadata, PathInits inits) {
        this(PlayerEntity.class, metadata, inits);
    }

    public QPlayerEntity(Class<? extends PlayerEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clan = inits.isInitialized("clan") ? new open.api.coc.clans.database.entity.clan.QClanEntity(forProperty("clan"), inits.get("clan")) : null;
        this.league = inits.isInitialized("league") ? new open.api.coc.clans.database.entity.league.QLeagueEntity(forProperty("league"), inits.get("league")) : null;
    }

}

