package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import static open.api.coc.clans.clean.infrastructure.player.persistence.entity.QPlayerEntity.playerEntity;
import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarEntity.clanWarEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarMemberAttackEntity.clanWarMemberAttackEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarMemberEntity.clanWarMemberEntity;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanWarParticipantRecordDTO;
import open.api.coc.clans.clean.domain.clan.model.ClanWarParticipationStatusRecordDTO;
import open.api.coc.clans.clean.domain.clan.model.query.ClanWarParticipationRecordSearchCriteria;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarParticipationStatusRecordResponse;
import open.api.coc.clans.database.entity.clan.ClanWarType;
import open.api.coc.clans.database.repository.clan.condition.ClanWarParticipationStatusRecordConditionBuilder;
import open.api.coc.clans.database.repository.clan.condition.ClanWarRecordConditionBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaClanWarMemberRecordQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ClanWarParticipantRecordDTO> findAll(ClanWarRecordConditionBuilder condition, Pageable pageable) {
        ConstructorExpression<ClanWarParticipantRecordDTO> clanWarRecordDTO = Projections.constructor(
            ClanWarParticipantRecordDTO.class,
            clanEntity.tag.max().as("clanTag"),
            clanEntity.name.max().as("clanName"),
            clanEntity.order.max().as("clanOrder"),
            playerEntity.playerTag.max().as("tag"),
            playerEntity.name.max().as("name"),
            playerEntity.townHallLevel.max().as("townHallLevel"),
            clanWarMemberAttackEntity.id.tag.count().as("attackCount"),
            clanWarMemberAttackEntity.destructionPercentage.sum().coalesce(0).as("totalDestructionPercentage"),
            clanWarMemberAttackEntity.duration.avg().coalesce(0.0).as("avgDuration"),
            clanWarMemberAttackEntity.stars.sum().coalesce(0).as("totalStars"),
            getStartSumByStarCount(3, "threeStars"),
            getStartSumByStarCount(2, "twoStars"),
            getStartSumByStarCount(1, "oneStars"),
            getStartSumByStarCount(0, "zeroStars")
        );

        JPAQuery<ClanWarParticipantRecordDTO> query = queryFactory.select(clanWarRecordDTO)
                                                                  .from(clanWarEntity)
                                                                  .join(clanWarEntity.members, clanWarMemberEntity)
                                                                  .leftJoin(clanWarMemberEntity.attacks, clanWarMemberAttackEntity)
                                                                  .join(playerEntity)
                                                                  .on(playerEntity.playerTag.eq(clanWarMemberEntity.id.tag))
                                                                  .join(clanEntity)
                                                                  .on(clanEntity.tag.eq(clanWarEntity.clanTag))
                                                                  .where(condition.build())
                                                                  .groupBy(clanWarMemberAttackEntity.id.tag)
                                                                  .orderBy(clanWarMemberAttackEntity.stars.sum().desc(),
                                                                      clanWarMemberAttackEntity.destructionPercentage.sum().desc(),
                                                                      clanWarMemberAttackEntity.duration.avg().asc());

        if (pageable != null && pageable.isPaged()) {
            query.offset(pageable.getOffset())
                 .limit(pageable.getPageSize());
        }

        return query.fetch();
    }

    private NumberExpression<Integer> getStartSumByStarCount(Integer starCount, String alias) {
        return new CaseBuilder().when(clanWarMemberAttackEntity.stars.eq(starCount))
                                .then(1)
                                .otherwise(0)
                                .sum()
                                .as(alias);
    }

    public List<ClanWarParticipationStatusRecordDTO> findParticipationRecords(ClanWarParticipationStatusRecordConditionBuilder condition) {
        ConstructorExpression<ClanWarParticipationStatusRecordDTO> clanWarParticipationStatusRecordDTO = Projections.constructor(
            ClanWarParticipationStatusRecordDTO.class,
            playerEntity.playerTag.as("playerTag"),
            playerEntity.name.max().as("playerName"),
            getClanWarCount(ClanWarType.NONE, "clanWarCount"),
            getClanWarCount(ClanWarType.PARALLEL, "parallelWarCount"),
            getClanWarCount(ClanWarType.LEAGUE, "leagueWarCount"),
            clanWarEntity.warId.countDistinct().as("totalParticipationCount")
        );

        JPAQuery<ClanWarParticipationStatusRecordDTO> query = queryFactory.select(clanWarParticipationStatusRecordDTO)
                                                                          .from(playerEntity)
                                                                          .leftJoin(clanWarMemberEntity).on(clanWarMemberEntity.id.tag.eq(playerEntity.playerTag))
                                                                          .leftJoin(clanWarEntity).on(clanWarEntity.warId.eq(clanWarMemberEntity.id.warId))
                                                                          .where(condition.build())
                                                                          .groupBy(playerEntity.playerTag)
                                                                          .orderBy(clanWarEntity.warId.countDistinct().desc());

        return query.fetch();
    }

    private Expression<Integer> getClanWarCount(ClanWarType clanWarType, String alias) {
        NumberExpression<Integer> caseBuilder = new CaseBuilder()
            .when(clanWarEntity.type.eq(clanWarType)).then(1)
            .otherwise(0)
            .sum();

        return ExpressionUtils.as(caseBuilder, alias);
    }
}
