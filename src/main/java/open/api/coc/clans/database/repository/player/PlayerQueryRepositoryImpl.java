package open.api.coc.clans.database.repository.player;

import static open.api.coc.clans.database.entity.clan.QClanBadgeEntity.clanBadgeEntity;
import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;
import static open.api.coc.clans.database.entity.league.QLeagueEntity.leagueEntity;
import static open.api.coc.clans.database.entity.player.QPlayerEntity.playerEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.player.PlayerEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlayerQueryRepositoryImpl implements PlayerQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PlayerEntity> findAll() {
        return queryFactory.select(playerEntity)
                           .from(playerEntity)
                           .leftJoin(playerEntity.league, leagueEntity)
                           .fetchJoin()
                           .leftJoin(playerEntity.clan, clanEntity)
                           .fetchJoin()
                           .leftJoin(clanEntity.badgeUrl, clanBadgeEntity)
                           .fetchJoin()
                           .fetch();
    }
}
