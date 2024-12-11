package open.api.coc.clans.database.entity.clan;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClanWarMemberEntity is a Querydsl query type for ClanWarMemberEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClanWarMemberEntity extends EntityPathBase<ClanWarMemberEntity> {

    private static final long serialVersionUID = -2027272145L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClanWarMemberEntity clanWarMemberEntity = new QClanWarMemberEntity("clanWarMemberEntity");

    public final ListPath<ClanWarMemberAttackEntity, QClanWarMemberAttackEntity> attacks = this.<ClanWarMemberAttackEntity, QClanWarMemberAttackEntity>createList("attacks", ClanWarMemberAttackEntity.class, QClanWarMemberAttackEntity.class, PathInits.DIRECT2);

    public final QClanWarEntity clanWar;

    public final QClanWarMemberPKEntity id;

    public final NumberPath<Integer> mapPosition = createNumber("mapPosition", Integer.class);

    public final StringPath name = createString("name");

    public final EnumPath<open.api.coc.clans.database.entity.common.YnType> necessaryAttackYn = createEnum("necessaryAttackYn", open.api.coc.clans.database.entity.common.YnType.class);

    public QClanWarMemberEntity(String variable) {
        this(ClanWarMemberEntity.class, forVariable(variable), INITS);
    }

    public QClanWarMemberEntity(Path<? extends ClanWarMemberEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClanWarMemberEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClanWarMemberEntity(PathMetadata metadata, PathInits inits) {
        this(ClanWarMemberEntity.class, metadata, inits);
    }

    public QClanWarMemberEntity(Class<? extends ClanWarMemberEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clanWar = inits.isInitialized("clanWar") ? new QClanWarEntity(forProperty("clanWar")) : null;
        this.id = inits.isInitialized("id") ? new QClanWarMemberPKEntity(forProperty("id")) : null;
    }

}

