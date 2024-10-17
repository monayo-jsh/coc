package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import static open.api.coc.clans.database.entity.clan.QClanBadgeEntity.clanBadgeEntity;
import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;
import static open.api.coc.clans.database.entity.clan.QClanLeagueAssignedPlayerEntity.clanLeagueAssignedPlayerEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanLeagueAssignedPlayerEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaClanLeagueAssignedPlayerQueryRepository {

    private final JPAQueryFactory queryFactory;

    private static final DateTimeFormatter SEASON_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    public String findLatestSeasonDate() {
        String maxSeasonDate = queryFactory.select(clanLeagueAssignedPlayerEntity.id.seasonDate.max())
                                           .from(clanLeagueAssignedPlayerEntity)
                                           .fetchOne();

        return Optional.ofNullable(maxSeasonDate)
                       .orElseGet(() -> LocalDate.now().format(SEASON_DATE_FORMATTER));
    }

    public List<ClanLeagueAssignedPlayerEntity> findAllBySeasonDate(String seasonDate) {
        return queryFactory.select(clanLeagueAssignedPlayerEntity)
                           .from(clanLeagueAssignedPlayerEntity)
                           .leftJoin(clanLeagueAssignedPlayerEntity.clan, clanEntity).fetchJoin()
                           .leftJoin(clanEntity.badgeUrl, clanBadgeEntity).fetchJoin()
                           .where(clanLeagueAssignedPlayerEntity.id.seasonDate.eq(seasonDate))
                           .fetch();
    }

    public long deleteBySeasonDateAndPlayerTags(String seasonDate, List<String> playerTags) {
        return queryFactory.delete(clanLeagueAssignedPlayerEntity)
                           .where(clanLeagueAssignedPlayerEntity.id.seasonDate.eq(seasonDate)
                                                                              .and(clanLeagueAssignedPlayerEntity.id.playerTag.in(playerTags)))
                           .execute();

    }
}
