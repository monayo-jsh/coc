package open.api.coc.clans.clean.infrastructure.competition.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompetitionClanRoasterEntity is a Querydsl query type for CompetitionClanRoasterEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompetitionClanRoasterEntity extends EntityPathBase<CompetitionClanRoasterEntity> {

    private static final long serialVersionUID = -180501145L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompetitionClanRoasterEntity competitionClanRoasterEntity = new QCompetitionClanRoasterEntity("competitionClanRoasterEntity");

    public final QCompetitionClanEntity competitionClan;

    public final QCompetitionClanRoasterPK id;

    public QCompetitionClanRoasterEntity(String variable) {
        this(CompetitionClanRoasterEntity.class, forVariable(variable), INITS);
    }

    public QCompetitionClanRoasterEntity(Path<? extends CompetitionClanRoasterEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompetitionClanRoasterEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompetitionClanRoasterEntity(PathMetadata metadata, PathInits inits) {
        this(CompetitionClanRoasterEntity.class, metadata, inits);
    }

    public QCompetitionClanRoasterEntity(Class<? extends CompetitionClanRoasterEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.competitionClan = inits.isInitialized("competitionClan") ? new QCompetitionClanEntity(forProperty("competitionClan"), inits.get("competitionClan")) : null;
        this.id = inits.isInitialized("id") ? new QCompetitionClanRoasterPK(forProperty("id")) : null;
    }

}

