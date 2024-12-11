package open.api.coc.clans.clean.infrastructure.competition.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompetitionClanRoundParticipantPK is a Querydsl query type for CompetitionClanRoundParticipantPK
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCompetitionClanRoundParticipantPK extends BeanPath<CompetitionClanRoundParticipantPK> {

    private static final long serialVersionUID = 403399790L;

    public static final QCompetitionClanRoundParticipantPK competitionClanRoundParticipantPK = new QCompetitionClanRoundParticipantPK("competitionClanRoundParticipantPK");

    public final NumberPath<Long> compClanScheduleId = createNumber("compClanScheduleId", Long.class);

    public final StringPath playerTag = createString("playerTag");

    public QCompetitionClanRoundParticipantPK(String variable) {
        super(CompetitionClanRoundParticipantPK.class, forVariable(variable));
    }

    public QCompetitionClanRoundParticipantPK(Path<? extends CompetitionClanRoundParticipantPK> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompetitionClanRoundParticipantPK(PathMetadata metadata) {
        super(CompetitionClanRoundParticipantPK.class, metadata);
    }

}

