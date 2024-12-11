package open.api.coc.clans.database.entity.clan;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClanEntity is a Querydsl query type for ClanEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClanEntity extends EntityPathBase<ClanEntity> {

    private static final long serialVersionUID = -442611047L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClanEntity clanEntity = new QClanEntity("clanEntity");

    public final QClanBadgeEntity badgeUrl;

    public final QClanContentEntity clanContent;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> order = createNumber("order", Integer.class);

    public final ListPath<open.api.coc.clans.database.entity.player.PlayerEntity, open.api.coc.clans.database.entity.player.QPlayerEntity> players = this.<open.api.coc.clans.database.entity.player.PlayerEntity, open.api.coc.clans.database.entity.player.QPlayerEntity>createList("players", open.api.coc.clans.database.entity.player.PlayerEntity.class, open.api.coc.clans.database.entity.player.QPlayerEntity.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final StringPath tag = createString("tag");

    public final EnumPath<open.api.coc.clans.database.entity.common.YnType> visibleYn = createEnum("visibleYn", open.api.coc.clans.database.entity.common.YnType.class);

    public final StringPath warLeague = createString("warLeague");

    public QClanEntity(String variable) {
        this(ClanEntity.class, forVariable(variable), INITS);
    }

    public QClanEntity(Path<? extends ClanEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClanEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClanEntity(PathMetadata metadata, PathInits inits) {
        this(ClanEntity.class, metadata, inits);
    }

    public QClanEntity(Class<? extends ClanEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.badgeUrl = inits.isInitialized("badgeUrl") ? new QClanBadgeEntity(forProperty("badgeUrl"), inits.get("badgeUrl")) : null;
        this.clanContent = inits.isInitialized("clanContent") ? new QClanContentEntity(forProperty("clanContent")) : null;
    }

}

