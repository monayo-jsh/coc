package open.api.coc.clans.database.entity.clan;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClanWarMemberAttackEntity is a Querydsl query type for ClanWarMemberAttackEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClanWarMemberAttackEntity extends EntityPathBase<ClanWarMemberAttackEntity> {

    private static final long serialVersionUID = -461184105L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClanWarMemberAttackEntity clanWarMemberAttackEntity = new QClanWarMemberAttackEntity("clanWarMemberAttackEntity");

    public final QClanWarMemberEntity clanWarMember;

    public final NumberPath<Integer> destructionPercentage = createNumber("destructionPercentage", Integer.class);

    public final NumberPath<Integer> duration = createNumber("duration", Integer.class);

    public final QClanWarMemberAttackPKEntity id;

    public final NumberPath<Integer> stars = createNumber("stars", Integer.class);

    public QClanWarMemberAttackEntity(String variable) {
        this(ClanWarMemberAttackEntity.class, forVariable(variable), INITS);
    }

    public QClanWarMemberAttackEntity(Path<? extends ClanWarMemberAttackEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClanWarMemberAttackEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClanWarMemberAttackEntity(PathMetadata metadata, PathInits inits) {
        this(ClanWarMemberAttackEntity.class, metadata, inits);
    }

    public QClanWarMemberAttackEntity(Class<? extends ClanWarMemberAttackEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clanWarMember = inits.isInitialized("clanWarMember") ? new QClanWarMemberEntity(forProperty("clanWarMember"), inits.get("clanWarMember")) : null;
        this.id = inits.isInitialized("id") ? new QClanWarMemberAttackPKEntity(forProperty("id")) : null;
    }

}

