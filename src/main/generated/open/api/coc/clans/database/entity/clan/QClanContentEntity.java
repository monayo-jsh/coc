package open.api.coc.clans.database.entity.clan;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClanContentEntity is a Querydsl query type for ClanContentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClanContentEntity extends EntityPathBase<ClanContentEntity> {

    private static final long serialVersionUID = -750008762L;

    public static final QClanContentEntity clanContentEntity = new QClanContentEntity("clanContentEntity");

    public final StringPath clanCapitalYn = createString("clanCapitalYn");

    public final StringPath clanWarParallelYn = createString("clanWarParallelYn");

    public final StringPath clanWarYn = createString("clanWarYn");

    public final StringPath tag = createString("tag");

    public final StringPath warLeagueYn = createString("warLeagueYn");

    public QClanContentEntity(String variable) {
        super(ClanContentEntity.class, forVariable(variable));
    }

    public QClanContentEntity(Path<? extends ClanContentEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClanContentEntity(PathMetadata metadata) {
        super(ClanContentEntity.class, metadata);
    }

}

