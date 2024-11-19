package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarEntity.clanWarEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanWarDTO;
import open.api.coc.clans.clean.domain.clan.model.ClanWarStatus;
import open.api.coc.clans.clean.infrastructure.clan.persistence.dto.LeagueWarRoundCountDTO;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import open.api.coc.clans.database.entity.clan.ClanWarType;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaClanWarQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<ClanWarEntity> findById(Long warId) {
        ClanWarEntity findClanWar = queryFactory.select(clanWarEntity)
                                                .from(clanWarEntity)
                                                .where(clanWarEntity.warId.eq(warId))
                                                .fetchOne();

        return Optional.ofNullable(findClanWar);
    }

    public Optional<ClanWarDTO> findDTOById(Long warId) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(clanWarEntity.warId.eq(warId));

        ClanWarDTO clanWar = createClanWarDTOQuery().where(condition)
                                                    .fetchOne();

        return Optional.ofNullable(clanWar);
    }

    public List<ClanWarDTO> findAllDTOByStartTime(LocalDateTime from, LocalDateTime to) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(clanWarEntity.startTime.between(from, to));

        return createClanWarDTOQuery().where(condition)
                                      .orderBy(clanWarEntity.warId.asc())
                                      .fetch();
    }

    public Optional<ClanWarDTO> findDTOByClanTagAndStartTime(String clanTag, LocalDateTime startTime) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(clanWarEntity.clanTag.eq(clanTag))
                 .and(clanWarEntity.startTime.eq(startTime));

        ClanWarDTO clanWar = createClanWarDTOQuery().where(condition).fetchOne();

        return Optional.ofNullable(clanWar);
    }

    private JPAQuery<ClanWarDTO> createClanWarDTOQuery() {
        ConstructorExpression<ClanWarDTO> clanWarDTO = projectionClanWarDTO();

        return queryFactory.select(clanWarDTO)
                           .from(clanWarEntity)
                           .join(clanEntity).on(clanEntity.tag.eq(clanWarEntity.clanTag));
    }

    private ConstructorExpression<ClanWarDTO> projectionClanWarDTO() {
        return Projections.constructor(ClanWarDTO.class,
                                       clanWarEntity.warId,
                                       clanEntity.name.as("clanName"),
                                       clanWarEntity.state,
                                       clanWarEntity.type,
                                       clanWarEntity.battleType,
                                       clanWarEntity.preparationStartTime,
                                       clanWarEntity.startTime,
                                       clanWarEntity.endTime,
                                       clanWarEntity.teamSize,
                                       clanWarEntity.attacksPerMember,
                                       clanWarEntity.warTag);
    }

    public List<LeagueWarRoundCountDTO> findLeagueWarRoundCounts(LocalDateTime from, LocalDateTime to) {
        ConstructorExpression<LeagueWarRoundCountDTO> leagueWarRoundCountDTO = Projections.constructor(LeagueWarRoundCountDTO.class,
                                                                                                       clanWarEntity.clanTag,
                                                                                                       clanWarEntity.clanTag.count().intValue());

        BooleanBuilder condition = new BooleanBuilder();
        condition.and(clanWarEntity.type.eq(ClanWarType.LEAGUE))
                 .and(clanWarEntity.state.ne(ClanWarStatus.preparation.name()))
                 .and(clanWarEntity.preparationStartTime.between(from, to));

        return queryFactory.select(leagueWarRoundCountDTO)
                           .from(clanWarEntity)
                           .where(condition)
                           .groupBy(clanWarEntity.clanTag)
                           .fetch();
    }
}
