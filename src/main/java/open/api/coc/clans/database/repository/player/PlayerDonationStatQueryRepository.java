package open.api.coc.clans.database.repository.player;

import static open.api.coc.clans.database.entity.player.QPlayerDonationStatEntity.playerDonationStatEntity;
import static open.api.coc.clans.database.entity.player.QPlayerEntity.playerEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.player.PlayerDonationStatEntity;
import open.api.coc.clans.domain.ranking.RankingHallOfFameDTO;
import open.api.coc.clans.domain.ranking.RankingHallOfFameDonationDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class PlayerDonationStatQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final PlayerDonationStatRepository playerDonationStatRepository;

    public Optional<PlayerDonationStatEntity> findByPlayerTagAndSeason(String playerTag, String season) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(playerDonationStatEntity.playerTag.eq(playerTag))
                 .and(playerDonationStatEntity.season.eq(season));

        return Optional.ofNullable(queryFactory.select(playerDonationStatEntity)
                                               .from(playerDonationStatEntity)
                                               .where(condition)
                                               .fetchOne());
    }

    public PlayerDonationStatEntity save(PlayerDonationStatEntity entity) {
        // JPA 기본 메서드로 처리
        return playerDonationStatRepository.save(entity);
    }

    public List<RankingHallOfFameDonationDTO> findRankingDonationsBySeasonAndPage(String season, Pageable page) {
        if (StringUtils.isEmpty(season)) {
            throw new IllegalArgumentException("season is not empty");
        }

        ConstructorExpression<RankingHallOfFameDonationDTO> rankingHallOfFameDTO = Projections.constructor(
            RankingHallOfFameDonationDTO.class,
            playerDonationStatEntity.playerTag.as("tag"),
            playerEntity.name.as("name"),
            playerDonationStatEntity.donationsDelta.as("score"),
            playerEntity.supportYn.as("supportYn")
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
