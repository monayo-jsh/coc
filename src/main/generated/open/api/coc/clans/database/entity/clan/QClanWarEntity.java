package open.api.coc.clans.database.entity.clan;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClanWarEntity is a Querydsl query type for ClanWarEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClanWarEntity extends EntityPathBase<ClanWarEntity> {

    private static final long serialVersionUID = 1851482741L;

    public static final QClanWarEntity clanWarEntity = new QClanWarEntity("clanWarEntity");

    public final NumberPath<Integer> attacksPerMember = createNumber("attacksPerMember", Integer.class);

    public final StringPath battleType = createString("battleType");

    public final StringPath clanTag = createString("clanTag");

    public final DateTimePath<java.time.LocalDateTime> endTime = createDateTime("endTime", java.time.LocalDateTime.class);

    public final ListPath<ClanWarMemberEntity, QClanWarMemberEntity> members = this.<ClanWarMemberEntity, QClanWarMemberEntity>createList("members", ClanWarMemberEntity.class, QClanWarMemberEntity.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> preparationStartTime = createDateTime("preparationStartTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    public final StringPath state = createString("state");

    public final NumberPath<Integer> teamSize = createNumber("teamSize", Integer.class);

    public final EnumPath<ClanWarType> type = createEnum("type", ClanWarType.class);

    public final NumberPath<Long> warId = createNumber("warId", Long.class);

    public final StringPath warTag = createString("warTag");

    public QClanWarEntity(String variable) {
        super(ClanWarEntity.class, forVariable(variable));
    }

    public QClanWarEntity(Path<? extends ClanWarEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClanWarEntity(PathMetadata metadata) {
        super(ClanWarEntity.class, metadata);
    }

}

