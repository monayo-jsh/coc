package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import static open.api.coc.clans.clean.infrastructure.player.persistence.entity.QPlayerEntity.playerEntity;
import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarEntity.clanWarEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarMemberAttackEntity.clanWarMemberAttackEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarMemberEntity.clanWarMemberEntity;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanWarMemberRecordDTO;
import open.api.coc.clans.database.repository.clan.condition.ClanWarRecordConditionBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaClanWarMemberRecordQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ClanWarMemberRecordDTO> findAll(ClanWarRecordConditionBuilder condition, Pageable pageable) {
        ConstructorExpression<ClanWarMemberRecordDTO> clanWarRecordDTO = Projections.constructor(
            ClanWarMemberRecordDTO.class,
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

        JPAQuery<ClanWarMemberRecordDTO> query = queryFactory.select(clanWarRecordDTO)
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

}
