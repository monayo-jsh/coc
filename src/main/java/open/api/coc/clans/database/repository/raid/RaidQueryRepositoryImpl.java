package open.api.coc.clans.database.repository.raid;

import static open.api.coc.clans.database.entity.clan.QClanBadgeEntity.clanBadgeEntity;
import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;
import static open.api.coc.clans.database.entity.raid.QRaidEntity.raidEntity;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.raid.RaidEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RaidQueryRepositoryImpl implements RaidQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<RaidEntity> findAllByStartDate(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate is not null.");
        }

        List<Tuple> results = queryFactory.select(raidEntity, clanEntity)
                                          .from(raidEntity)
                                          .join(clanEntity).on(clanEntity.tag.eq(raidEntity.clanTag))
                                          .leftJoin(clanEntity.badgeUrl, clanBadgeEntity).fetchJoin()
                                          .where(raidEntity.startDate.eq(startDate))
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
}
