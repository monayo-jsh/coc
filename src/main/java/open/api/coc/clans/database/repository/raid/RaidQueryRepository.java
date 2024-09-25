package open.api.coc.clans.database.repository.raid;

import static open.api.coc.clans.clean.infrastructure.capital.persistence.entity.QRaidEntity.raidEntity;
import static open.api.coc.clans.database.entity.clan.QClanBadgeEntity.clanBadgeEntity;
import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.capital.persistence.entity.RaidEntity;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RaidQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<RaidEntity> findAllByStartDate(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate is not null.");
        }

        List<Tuple> results = queryFactory.select(raidEntity, clanEntity)
                                          .from(raidEntity)
                                          .join(clanEntity).on(clanEntity.tag.eq(raidEntity.clanTag))
                                          .leftJoin(clanEntity.badgeUrl, clanBadgeEntity).fetchJoin()
                                          .where(raidEntity.startDate.eq(startDate))
                                          .orderBy(clanEntity.order.asc())
                                          .fetch();

        List<RaidEntity> raidEntities = new ArrayList<>();
        for (Tuple tuple : results) {
            RaidEntity raid = tuple.get(raidEntity);
            ClanEntity clan = tuple.get(clanEntity);
            raid.changeClan(clan);
            raidEntities.add(raid);
        }

        return raidEntities;
    }

    public LocalDate getCurrentSeason() {
        return queryFactory.select(raidEntity.startDate.max())
                           .from(raidEntity)
                           .fetchOne();
    }

    public List<LocalDate> findLatestSeasonByPage(Pageable page) {
        if (page == null) {
            throw new IllegalArgumentException("page is not null");
        }

        return queryFactory.select(raidEntity.startDate)
                           .from(raidEntity)
                           .groupBy(raidEntity.startDate)
                           .orderBy(raidEntity.startDate.desc())
                           .offset(page.getOffset())
                           .limit(page.getPageSize())
                           .fetch();
    }
}
