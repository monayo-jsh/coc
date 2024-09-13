package open.api.coc.clans.database.entity.clan;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClanLeagueWarEntity is a Querydsl query type for ClanLeagueWarEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClanLeagueWarEntity extends EntityPathBase<ClanLeagueWarEntity> {

    private static final long serialVersionUID = -787126906L;

    public static final QClanLeagueWarEntity clanLeagueWarEntity = new QClanLeagueWarEntity("clanLeagueWarEntity");

    public final StringPath clanTag = createString("clanTag");

    public final NumberPath<Long> leagueWarId = createNumber("leagueWarId", Long.class);

    public final StringPath season = createString("season");

    public final StringPath warLeague = createString("warLeague");

    public QClanLeagueWarEntity(String variable) {
        super(ClanLeagueWarEntity.class, forVariable(variable));
    }

    public QClanLeagueWarEntity(Path<? extends ClanLeagueWarEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClanLeagueWarEntity(PathMetadata metadata) {
        super(ClanLeagueWarEntity.class, metadata);
    }

}

