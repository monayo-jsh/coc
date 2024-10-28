package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import static open.api.coc.clans.clean.infrastructure.player.persistence.entity.QPlayerDonationStatEntity.playerDonationStatEntity;
import static open.api.coc.clans.clean.infrastructure.player.persistence.entity.QPlayerEntity.playerEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerDonationDTO;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerDonationStatEntity;
import open.api.coc.clans.domain.ranking.RankingHallOfFameDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

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
            playerDonationStatEntity.donationsDelta.as("score")
        );

        return queryFactory.select(rankingHallOfFameDTO)
                           .from(playerDonationStatEntity)
                           .join(playerEntity).on(playerEntity.playerTag.eq(playerDonationStatEntity.playerTag)).fetchJoin()
                           .where(playerDonationStatEntity.season.eq(season))
                           .orderBy(playerDonationStatEntity.donationsDelta.desc())
                           .offset(page.getOffset())
                           .limit(page.getPageSize())
                           .fetch();
    }

    public List<RankingHallOfFameDTO> findRankingDonationsReceivedBySeasonAndPage(String season, PageRequest page) {
        if (StringUtils.isEmpty(season)) {
            throw new IllegalArgumentException("season is not empty");
        }

        ConstructorExpression<RankingHallOfFameDTO> rankingHallOfFameDTO = Projections.constructor(
            RankingHallOfFameDTO.class,
            playerDonationStatEntity.playerTag.as("tag"),
            playerEntity.name.as("name"),
            playerDonationStatEntity.donationsReceivedDelta.as("score")
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
