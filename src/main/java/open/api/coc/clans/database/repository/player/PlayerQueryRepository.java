package open.api.coc.clans.database.repository.player;

import static open.api.coc.clans.clean.infrastructure.league.persistence.entity.QLeagueEntity.leagueEntity;
import static open.api.coc.clans.clean.infrastructure.player.persistence.entity.QPlayerEntity.playerEntity;
import static open.api.coc.clans.database.entity.clan.QClanBadgeEntity.clanBadgeEntity;
import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;
import static open.api.coc.clans.database.entity.player.QPlayerRecordEntity.playerRecordEntity;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerEntity;
import open.api.coc.clans.database.entity.common.YnType;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlayerQueryRepository {

    private final PlayerRepository playerRepository;
    private final JPAQueryFactory queryFactory;

    private JPAQuery<PlayerEntity> createBaseQuery() {
        return queryFactory.selectFrom(playerEntity)
                           .leftJoin(playerEntity.league, leagueEntity).fetchJoin()
                           .leftJoin(playerEntity.clan, clanEntity).fetchJoin()
                           .leftJoin(clanEntity.badgeUrl, clanBadgeEntity).fetchJoin();
    }

    public List<PlayerEntity> findAll() {
        JPAQuery<PlayerEntity> query = createBaseQuery();
        return query.fetch();
    }

    public List<PlayerEntity> findAllByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be null or empty");
        }

        JPAQuery<PlayerEntity> query = createBaseQuery().where(playerEntity.name.startsWith(name));
        return query.fetch();
    }

    public long resetSupportPlayers() {

        return queryFactory.update(playerEntity)
                           .set(playerEntity.supportYn, YnType.N)
                           .where(playerEntity.supportYn.eq(YnType.Y))
                           .execute();

    }

    public long updateSupportPlayers(List<String> supportPlayerTags) {
        return queryFactory.update(playerEntity)
                           .set(playerEntity.supportYn, YnType.Y)
                           .where(playerEntity.playerTag.in(supportPlayerTags))
                           .execute();
    }

    public List<String> findAllNotExistsByPlayerTags(List<String> playerTags) {

        List<String> existsPlayerTags = queryFactory.select(playerEntity.playerTag)
                                                    .from(playerEntity)
                                                    .where(playerEntity.playerTag.in(playerTags))
                                                    .fetch();

        return playerTags.stream()
                         .filter(playerTag -> !existsPlayerTags.contains(playerTag))
                         .collect(Collectors.toList());

    }

    public PlayerEntity save(PlayerEntity playerEntity) {
        return playerRepository.save(playerEntity);
    }

    public long resetAllPlayerDonations() {
        return queryFactory.update(playerEntity)
                           .set(playerEntity.donations, 0)
                           .set(playerEntity.donationsReceived, 0)
                           .execute();
    }

    public List<PlayerEntity> findAllWithoutRecordTarget() {
        return queryFactory.select(playerEntity)
                           .from(playerEntity)
                           .leftJoin(playerRecordEntity).on(playerRecordEntity.tag.eq(playerEntity.playerTag))
                           .where(playerRecordEntity.tag.isNull())
                           .fetch();
    }
}
