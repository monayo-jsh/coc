package open.api.coc.clans.clean.infrastructure.competition.persistence.repository;

import static open.api.coc.clans.clean.infrastructure.competition.persistence.entity.QCompetitionClanEntity.competitionClanEntity;
import static open.api.coc.clans.clean.infrastructure.competition.persistence.entity.QCompetitionEntity.competitionEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaCompetitionCustomRepository {

    private final JPAQueryFactory queryFactory;


    public Optional<CompetitionEntity> findById(Long id) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(competitionEntity.id.eq(id));

        CompetitionEntity competitionEntity = createBaseEntityJPAQuery().where(condition)
                                                                        .fetchOne();

        return Optional.ofNullable(competitionEntity);
    }

    private JPAQuery<CompetitionEntity> createBaseEntityJPAQuery() {
        return queryFactory.select(competitionEntity)
                           .from(competitionEntity)
                           .leftJoin(competitionEntity.participantClans, competitionClanEntity)
                           .fetchJoin();
    }

}
