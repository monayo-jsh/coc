package open.api.coc.clans.database.repository.clan;

import static open.api.coc.clans.database.entity.clan.QClanLeagueWarEntity.clanLeagueWarEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanLeagueWarEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanLeagueWarQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ClanLeagueWarEntity> findAllBySeason(String season) {
        if (season == null || season.isBlank()) {
            throw new IllegalArgumentException("startDate is not null.");
        }

        BooleanBuilder leagueWarCondition = new BooleanBuilder();
        leagueWarCondition.and(clanLeagueWarEntity.season.eq(season));

        return queryFactory.select(clanLeagueWarEntity)
                           .from(clanLeagueWarEntity)
                           .where(leagueWarCondition)
                           .fetch();
    }
}
