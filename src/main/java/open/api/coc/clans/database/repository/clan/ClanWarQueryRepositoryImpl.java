package open.api.coc.clans.database.repository.clan;

import static open.api.coc.clans.database.entity.clan.QClanBadgeEntity.clanBadgeEntity;
import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarEntity.clanWarEntity;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanWarQueryRepositoryImpl implements ClanWarQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ClanWarEntity> findByWarId(Long warId) {
        ClanWarEntity clanWar = queryFactory.selectFrom(clanWarEntity)
                                            .where(clanWarEntity.warId.eq(warId))
                                            .fetchOne();

        if (clanWar != null) {
            ClanEntity clan = createClanBaseQuery().where(clanEntity.tag.eq(clanWar.getClanTag())).fetchOne();
            clanWar.changeClan(clan);
        }

        return Optional.ofNullable(clanWar);
    }

    @Override
    public List<ClanWarEntity> findAllByStartTimePeriod(LocalDateTime from, LocalDateTime to) {
        List<ClanWarEntity> clanWarEntities = queryFactory.selectFrom(clanWarEntity)
                                                          .where(clanWarEntity.startTime.between(from, to))
                                                          .orderBy(clanWarEntity.warId.asc())
                                                          .fetch();

        if (!clanWarEntities.isEmpty()) {
            Set<String> clanTags = clanWarEntities.stream().map(ClanWarEntity::getClanTag).collect(Collectors.toSet());
            Map<String, ClanEntity> clans = createClanBaseQuery().where(clanEntity.tag.in(clanTags)).fetch()
                                                                 .stream().collect(Collectors.toMap(ClanEntity::getTag, clan -> clan));


            clanWarEntities.forEach(clanWarEntity -> {
                ClanEntity clan = clans.get(clanWarEntity.getClanTag());
                clanWarEntity.changeClan(clan);
            });
        }

        return clanWarEntities;
    }

    private JPAQuery<ClanEntity> createClanBaseQuery() {
        return queryFactory.selectFrom(clanEntity)
                           .join(clanEntity.badgeUrl, clanBadgeEntity).fetchJoin();
    }
}
