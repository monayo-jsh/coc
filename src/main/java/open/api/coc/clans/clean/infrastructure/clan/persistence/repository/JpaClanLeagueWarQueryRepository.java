package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import static open.api.coc.clans.clean.infrastructure.clan.persistence.entity.QClanLeagueWarEntity.clanLeagueWarEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.clan.persistence.entity.ClanLeagueWarEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaClanLeagueWarQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ClanLeagueWarEntity> findAllBySeasonAndClanTagIn(String season, Set<String> clanTags) {
        BooleanBuilder leagueWarCondition = new BooleanBuilder();
        leagueWarCondition.and(clanLeagueWarEntity.season.eq(season))
                          .and(clanLeagueWarEntity.clanTag.in(clanTags));

        return queryFactory.select(clanLeagueWarEntity)
                           .from(clanLeagueWarEntity)
                           .where(leagueWarCondition)
                           .fetch();
    }

}
