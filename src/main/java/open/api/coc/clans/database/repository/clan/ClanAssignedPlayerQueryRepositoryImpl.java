package open.api.coc.clans.database.repository.clan;

import static open.api.coc.clans.database.entity.clan.QClanAssignedPlayerEntity.clanAssignedPlayerEntity;
import static open.api.coc.clans.database.entity.clan.QClanBadgeEntity.clanBadgeEntity;
import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanAssignedPlayerQueryRepositoryImpl implements ClanAssignedPlayerQueryRepository {

    private final JPAQueryFactory queryFactory;

    private static final DateTimeFormatter SEASON_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    @Override
    public String findLatestSeasonDate() {
        String maxSeasonDate = queryFactory.select(clanAssignedPlayerEntity.id.seasonDate.max())
                                           .from(clanAssignedPlayerEntity)
                                           .fetchOne();

        return Optional.ofNullable(maxSeasonDate)
                       .orElseGet(() -> LocalDate.now().format(SEASON_DATE_FORMATTER));
    }

    @Override
    public List<ClanAssignedPlayerEntity> findAllBySeasonDate(String seasonDate) {
        return queryFactory.select(clanAssignedPlayerEntity)
                           .from(clanAssignedPlayerEntity)
                           .leftJoin(clanAssignedPlayerEntity.clan, clanEntity)
                           .fetchJoin()
                           .leftJoin(clanEntity.badgeUrl, clanBadgeEntity)
                           .fetchJoin()
                           .where(clanAssignedPlayerEntity.id.seasonDate.eq(seasonDate))
                           .fetch();
    }

}
