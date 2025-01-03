package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import static open.api.coc.clans.clean.infrastructure.player.persistence.entity.QPlayerDonationStatEntity.playerDonationStatEntity;
import static open.api.coc.clans.clean.infrastructure.player.persistence.entity.QPlayerEntity.playerEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerDonationDTO;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerDonationReceiveDTO;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerDonationStatEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaPlayerDonationStatQueryRepository {

    private final JPAQueryFactory queryFactory;
    public Optional<PlayerDonationStatEntity> findByPlayerTagAndSeason(String playerTag, String season) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(playerDonationStatEntity.playerTag.eq(playerTag))
                 .and(playerDonationStatEntity.season.eq(season));

        PlayerDonationStatEntity findEntity = queryFactory.select(playerDonationStatEntity)
                                                          .from(playerDonationStatEntity)
                                                          .where(condition)
                                                          .fetchOne();

        return Optional.ofNullable(findEntity);
    }

    public List<PlayerDonationDTO> findRankingDonationsBySeasonAndPage(String season, Pageable page) {
        ConstructorExpression<PlayerDonationDTO> rankingHallOfFameDTO = Projections.constructor(
            PlayerDonationDTO.class,
            playerEntity.playerTag.as("tag"),
            playerEntity.name.as("name"),
            playerEntity.townHallLevel.as("townHallLevel"),
            playerEntity.supportYn.as("supportYn"),
            playerDonationStatEntity.newTroops.subtract(playerDonationStatEntity.oldTroops).as("troopCount"),
            playerDonationStatEntity.newSpells.subtract(playerDonationStatEntity.oldSpells).as("spellCount"),
            playerDonationStatEntity.newSieges.subtract(playerDonationStatEntity.oldSieges).as("siegeCount")
        );

        // 정렬 조건 - 유닛 지원 수 + 마법 지원 수 + 시즈머신 지원 수 내림차순
        OrderSpecifier<?> orderBy = playerDonationStatEntity.newTroops.subtract(playerDonationStatEntity.oldTroops)
                                                                      .add(playerDonationStatEntity.newSpells.subtract(playerDonationStatEntity.oldSpells))
                                                                      .add(playerDonationStatEntity.newSieges.subtract(playerDonationStatEntity.oldSieges))
                                                                      .desc();
        return queryFactory.select(rankingHallOfFameDTO)
                           .from(playerDonationStatEntity)
                           .join(playerEntity).on(playerEntity.playerTag.eq(playerDonationStatEntity.playerTag)).fetchJoin()
                           .where(playerDonationStatEntity.season.eq(season))
                           .orderBy(orderBy)
                           .offset(page.getOffset())
                           .limit(page.getPageSize())
                           .fetch();
    }

    public List<PlayerDonationReceiveDTO> findRankingDonationsReceivedBySeasonAndPage(String season, Pageable page) {
        ConstructorExpression<PlayerDonationReceiveDTO> rankingHallOfFameDTO = Projections.constructor(
            PlayerDonationReceiveDTO.class,
            playerEntity.playerTag.as("tag"),
            playerEntity.name.as("name"),
            playerEntity.townHallLevel.as("townHallLevel"),
            playerDonationStatEntity.donationsReceivedDelta.as("count")
        );

        return queryFactory.select(rankingHallOfFameDTO)
                           .from(playerDonationStatEntity)
                           .join(playerEntity).on(playerEntity.playerTag.eq(playerDonationStatEntity.playerTag)).fetchJoin()
                           .where(playerDonationStatEntity.season.eq(season))
                           .orderBy(playerDonationStatEntity.donationsReceivedDelta.desc())
                           .offset(page.getOffset())
                           .limit(page.getPageSize())
                           .fetch();
    }
}
