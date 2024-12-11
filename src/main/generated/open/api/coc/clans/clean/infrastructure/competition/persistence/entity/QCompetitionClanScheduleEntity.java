package open.api.coc.clans.clean.infrastructure.competition.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompetitionClanScheduleEntity is a Querydsl query type for CompetitionClanScheduleEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompetitionClanScheduleEntity extends EntityPathBase<CompetitionClanScheduleEntity> {

    private static final long serialVersionUID = 1584919368L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompetitionClanScheduleEntity competitionClanScheduleEntity = new QCompetitionClanScheduleEntity("competitionClanScheduleEntity");

    public final QCompetitionClanEntity competitionClan;

    public final StringPath description = createString("description");

    public final DatePath<java.time.LocalDate> end_date = createDate("end_date", java.time.LocalDate.class);

    public final DateTimePath<java.time.LocalDateTime> fixedAt = createDateTime("fixedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<CompetitionClanRoundParticipantEntity, QCompetitionClanRoundParticipantEntity> participants = this.<CompetitionClanRoundParticipantEntity, QCompetitionClanRoundParticipantEntity>createList("participants", CompetitionClanRoundParticipantEntity.class, QCompetitionClanRoundParticipantEntity.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public QCompetitionClanScheduleEntity(String variable) {
        this(CompetitionClanScheduleEntity.class, forVariable(variable), INITS);
    }

    public QCompetitionClanScheduleEntity(Path<? extends CompetitionClanScheduleEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompetitionClanScheduleEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompetitionClanScheduleEntity(PathMetadata metadata, PathInits inits) {
        this(CompetitionClanScheduleEntity.class, metadata, inits);
    }

    public QCompetitionClanScheduleEntity(Class<? extends CompetitionClanScheduleEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.competitionClan = inits.isInitialized("competitionClan") ? new QCompetitionClanEntity(forProperty("competitionClan"), inits.get("competitionClan")) : null;
    }

}

