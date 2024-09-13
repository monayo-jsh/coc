package open.api.coc.clans.clean.infrastructure.competition.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompetitionEntity is a Querydsl query type for CompetitionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompetitionEntity extends EntityPathBase<CompetitionEntity> {

    private static final long serialVersionUID = 2034275803L;

    public static final QCompetitionEntity competitionEntity = new QCompetitionEntity("competitionEntity");

    public final StringPath discordUrl = createString("discordUrl");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath remarks = createString("remarks");

    public final StringPath restrictions = createString("restrictions");

    public final NumberPath<Integer> roasterSize = createNumber("roasterSize", Integer.class);

    public final StringPath ruleBookUrl = createString("ruleBookUrl");

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public QCompetitionEntity(String variable) {
        super(CompetitionEntity.class, forVariable(variable));
    }

    public QCompetitionEntity(Path<? extends CompetitionEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompetitionEntity(PathMetadata metadata) {
        super(CompetitionEntity.class, metadata);
    }

}

