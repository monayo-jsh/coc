package open.api.coc.clans.clean.infrastructure.competition.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompetitionClanRoundParticipantEntity is a Querydsl query type for CompetitionClanRoundParticipantEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompetitionClanRoundParticipantEntity extends EntityPathBase<CompetitionClanRoundParticipantEntity> {

    private static final long serialVersionUID = -1859798794L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompetitionClanRoundParticipantEntity competitionClanRoundParticipantEntity = new QCompetitionClanRoundParticipantEntity("competitionClanRoundParticipantEntity");

    public final QCompetitionClanScheduleEntity competitionClanSchedule;

    public final QCompetitionClanRoundParticipantPK id;

    public final BooleanPath isBackup = createBoolean("isBackup");

    public QCompetitionClanRoundParticipantEntity(String variable) {
        this(CompetitionClanRoundParticipantEntity.class, forVariable(variable), INITS);
    }

    public QCompetitionClanRoundParticipantEntity(Path<? extends CompetitionClanRoundParticipantEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompetitionClanRoundParticipantEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompetitionClanRoundParticipantEntity(PathMetadata metadata, PathInits inits) {
        this(CompetitionClanRoundParticipantEntity.class, metadata, inits);
    }

    public QCompetitionClanRoundParticipantEntity(Class<? extends CompetitionClanRoundParticipantEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.competitionClanSchedule = inits.isInitialized("competitionClanSchedule") ? new QCompetitionClanScheduleEntity(forProperty("competitionClanSchedule"), inits.get("competitionClanSchedule")) : null;
        this.id = inits.isInitialized("id") ? new QCompetitionClanRoundParticipantPK(forProperty("id")) : null;
    }

}

