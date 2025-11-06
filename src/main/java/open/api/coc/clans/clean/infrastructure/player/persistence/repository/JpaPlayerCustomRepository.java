package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import static open.api.coc.clans.clean.infrastructure.player.persistence.entity.QPlayerEntity.playerEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.model.dto.LeagueDistributionDTO;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerSearchQuery;
import open.api.coc.clans.clean.domain.player.model.dto.RankingHeroEquipmentDTO;
import open.api.coc.clans.clean.infrastructure.league.persistence.entity.LeagueEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerEntity;
import open.api.coc.clans.database.entity.common.YnType;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaPlayerCustomRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    public List<String> findAllTag(PlayerSearchQuery query) {
        BooleanBuilder condition = makeCondition(query);

        return queryFactory.select(playerEntity.playerTag)
                           .from(playerEntity)
                           .where(condition)
                           .fetch();
    }

    public List<PlayerEntity> findAll(PlayerSearchQuery query) {
        BooleanBuilder condition = makeCondition(query);

        return queryFactory.select(playerEntity)
                           .from(playerEntity)
                           .where(condition)
                           .fetch();
    }

    private BooleanBuilder makeCondition(PlayerSearchQuery query) {
        BooleanBuilder condition = new BooleanBuilder();
        if (query.isFilterSupport()) {
            condition.and(playerEntity.supportYn.eq(YnType.Y));
        }
        if (query.isNameSearch()) {
            condition.and(playerEntity.name.startsWith(query.name())
                                           .or(playerEntity.nickname.startsWith(query.name())));
        }
        if (query.isTagSearch()) {
            condition.and(playerEntity.playerTag.in(query.tags()));
        }
        return condition;
    }

    public List<PlayerEntity> findTrophiesRanking(Integer pageSize) {
        return queryFactory.select(playerEntity)
                           .from(playerEntity)
                           .orderBy(playerEntity.trophies.desc())
                           .offset(0)
                           .limit(pageSize)
                           .fetch();
    }

    public List<PlayerEntity> findTierTrophiesRanking(Integer pageSize) {
        return queryFactory.select(playerEntity)
                           .from(playerEntity)
                           .where(playerEntity.league.id.notIn(LeagueEntity.getUnrankedTierId(), LeagueEntity.getLegendTierId())) // 언랭크, 전설 리그
                           .orderBy(playerEntity.trophies.desc())
                           .offset(0)
                           .limit(pageSize)
                           .fetch();
    }


    public List<PlayerEntity> findAttackWinsRanking(Integer pageSize) {
        return queryFactory.select(playerEntity)
                           .from(playerEntity)
                           .orderBy(playerEntity.attackWins.desc())
                           .offset(0)
                           .limit(pageSize)
                           .fetch();
    }

    public void resetAllSupportType() {
        queryFactory.update(playerEntity)
                    .set(playerEntity.supportYn, YnType.N)
                    .where(playerEntity.supportYn.eq(YnType.Y))
                    .execute();
    }

    public long updateSupport(List<String> playerTags) {
        return queryFactory.update(playerEntity)
                           .set(playerEntity.supportYn, YnType.Y)
                           .where(playerEntity.playerTag.in(playerTags))
                           .execute();
    }

    public List<LeagueDistributionDTO> findLeagueDistribution() {
        String query = "select p.league_id, count(p.league_id) "
                    + "from tb_player p "
                    + "where p.league_id is not null "
                    + "group by p.league_id";

        List<Object[]> results = entityManager.createNativeQuery(query)
                                              .getResultList();

        return results.stream()
                      .map(LeagueDistributionDTO::create)
                      .toList();
    }
}
