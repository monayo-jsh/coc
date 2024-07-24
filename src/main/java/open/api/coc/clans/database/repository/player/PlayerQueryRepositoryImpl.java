package open.api.coc.clans.database.repository.player;

import static open.api.coc.clans.database.entity.clan.QClanBadgeEntity.clanBadgeEntity;
import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;
import static open.api.coc.clans.database.entity.league.QLeagueEntity.leagueEntity;
import static open.api.coc.clans.database.entity.player.QPlayerEntity.playerEntity;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.player.PlayerEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlayerQueryRepositoryImpl implements PlayerQueryRepository {

    private final JPAQueryFactory queryFactory;

    private JPAQuery<PlayerEntity> createBaseQuery() {
        return queryFactory.selectFrom(playerEntity)
                           .leftJoin(playerEntity.league, leagueEntity).fetchJoin()
                           .leftJoin(playerEntity.clan, clanEntity).fetchJoin()
                           .leftJoin(clanEntity.badgeUrl, clanBadgeEntity).fetchJoin();
    }

    @Override
    public List<PlayerEntity> findAll() {
        JPAQuery<PlayerEntity> query = createBaseQuery();
        return query.fetch();
    }

    @Override
    public List<PlayerEntity> findAllByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be null or empty");
        }

        JPAQuery<PlayerEntity> query = createBaseQuery().where(playerEntity.name.startsWith(name));
        return query.fetch();
    }
}
