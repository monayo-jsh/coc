package open.api.coc.clans.clean.infrastructure.competition.persistence.repository;


import static open.api.coc.clans.clean.infrastructure.competition.persistence.entity.QCompetitionClanEntity.competitionClanEntity;
import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.competition.persistence.dto.CompetitionClanDTO;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaCompetitionClanCustomRepository {

    private final JPAQueryFactory queryFactory;


    public List<CompetitionClanDTO> findWithClanNameByCompId(Long compId) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(competitionClanEntity.competition.id.eq(compId));

        ConstructorExpression<CompetitionClanDTO> competitionClanDTO = Projections.constructor(
            CompetitionClanDTO.class,
            competitionClanEntity.id.as("id"),
            competitionClanEntity.competition.id.as("compId"),
            competitionClanEntity.clanTag.as("clanTag"),
            clanEntity.name.as("clanName"),
            competitionClanEntity.status.as("status")
        );

        return queryFactory.select(competitionClanDTO)
                           .from(competitionClanEntity)
                           .join(clanEntity).on(clanEntity.tag.eq(competitionClanEntity.clanTag))
                           .where(condition)
                           .fetch();
    }
}
