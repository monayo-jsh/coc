package open.api.coc.clans.database.repository.player;

import static open.api.coc.clans.database.entity.player.QPlayerDonationStatEntity.playerDonationStatEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.player.PlayerDonationStatEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlayerDonationStatQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final PlayerDonationStatRepository playerDonationStatRepository;

    public Optional<PlayerDonationStatEntity> findByPlayerTagAndSeason(String playerTag, String seaon) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(playerDonationStatEntity.playerTag.eq(playerTag))
                 .and(playerDonationStatEntity.season.eq(seaon));

        return Optional.ofNullable(queryFactory.select(playerDonationStatEntity)
                                               .from(playerDonationStatEntity)
                                               .where(condition)
                                               .fetchOne());
    }

    public PlayerDonationStatEntity save(PlayerDonationStatEntity entity) {
        // JPA 기본 메서드로 처리
        return playerDonationStatRepository.save(entity);
    }

}
