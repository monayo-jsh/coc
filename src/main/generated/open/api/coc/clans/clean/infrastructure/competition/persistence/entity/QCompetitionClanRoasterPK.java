package open.api.coc.clans.clean.infrastructure.competition.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompetitionClanRoasterPK is a Querydsl query type for CompetitionClanRoasterPK
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCompetitionClanRoasterPK extends BeanPath<CompetitionClanRoasterPK> {

    private static final long serialVersionUID = 1840599391L;

    public static final QCompetitionClanRoasterPK competitionClanRoasterPK = new QCompetitionClanRoasterPK("competitionClanRoasterPK");

    public final NumberPath<Long> compClanId = createNumber("compClanId", Long.class);

    public final StringPath playerTag = createString("playerTag");

    public QCompetitionClanRoasterPK(String variable) {
        super(CompetitionClanRoasterPK.class, forVariable(variable));
    }

    public QCompetitionClanRoasterPK(Path<? extends CompetitionClanRoasterPK> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompetitionClanRoasterPK(PathMetadata metadata) {
        super(CompetitionClanRoasterPK.class, metadata);
    }

}

