package open.api.coc.clans.clean.infrastructure.competition.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompetitionClanEntity is a Querydsl query type for CompetitionClanEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompetitionClanEntity extends EntityPathBase<CompetitionClanEntity> {

    private static final long serialVersionUID = 168873937L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompetitionClanEntity competitionClanEntity = new QCompetitionClanEntity("competitionClanEntity");

    public final StringPath clanTag = createString("clanTag");

    public final QCompetitionEntity competition;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<CompetitionClanRoasterEntity, QCompetitionClanRoasterEntity> roasters = this.<CompetitionClanRoasterEntity, QCompetitionClanRoasterEntity>createList("roasters", CompetitionClanRoasterEntity.class, QCompetitionClanRoasterEntity.class, PathInits.DIRECT2);

    public final ListPath<CompetitionClanScheduleEntity, QCompetitionClanScheduleEntity> schedules = this.<CompetitionClanScheduleEntity, QCompetitionClanScheduleEntity>createList("schedules", CompetitionClanScheduleEntity.class, QCompetitionClanScheduleEntity.class, PathInits.DIRECT2);

    public QCompetitionClanEntity(String variable) {
        this(CompetitionClanEntity.class, forVariable(variable), INITS);
    }

    public QCompetitionClanEntity(Path<? extends CompetitionClanEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompetitionClanEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompetitionClanEntity(PathMetadata metadata, PathInits inits) {
        this(CompetitionClanEntity.class, metadata, inits);
    }

    public QCompetitionClanEntity(Class<? extends CompetitionClanEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.competition = inits.isInitialized("competition") ? new QCompetitionEntity(forProperty("competition")) : null;
    }

}

