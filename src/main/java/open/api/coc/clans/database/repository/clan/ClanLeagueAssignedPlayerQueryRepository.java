package open.api.coc.clans.database.repository.clan;

import static open.api.coc.clans.clean.infrastructure.player.persistence.entity.QPlayerEntity.playerEntity;
import static open.api.coc.clans.database.entity.clan.QClanAssignedPlayerEntity.clanAssignedPlayerEntity;
import static open.api.coc.clans.database.entity.clan.QClanBadgeEntity.clanBadgeEntity;
import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;
import static open.api.coc.clans.database.entity.clan.QClanLeagueAssignedPlayerEntity.clanLeagueAssignedPlayerEntity;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanLeagueAssignedPlayerQueryRepository {

    private final JPAQueryFactory queryFactory;

    private static final DateTimeFormatter SEASON_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    public String findLatestSeasonDate() {
        String maxSeasonDate = queryFactory.select(clanLeagueAssignedPlayerEntity.id.seasonDate.max())
                                           .from(clanLeagueAssignedPlayerEntity)
                                           .fetchOne();

        return Optional.ofNullable(maxSeasonDate)
                       .orElseGet(() -> LocalDate.now().format(SEASON_DATE_FORMATTER));
    }

    public List<ClanAssignedPlayerDTO> findAllBySeasonDate(String seasonDate) {
        ConstructorExpression<ClanAssignedPlayerDTO> clanAssignedPlayerDTO = Projections.constructor(ClanAssignedPlayerDTO.class,
                                                                                                     clanLeagueAssignedPlayerEntity.id.seasonDate.as("seasonDate"),
                                                                                                     clanLeagueAssignedPlayerEntity.id.playerTag.as("playerTag"),
                                                                                                     playerEntity.name.as("playerName"),
                                                                                                     playerEntity.trophies.as("trophies"),
                                                                                                     playerEntity.league.as("league"),
                                                                                                     clanLeagueAssignedPlayerEntity.clan.as("clan"),
                                                                                                     playerEntity.clan.as("joinedClan"));
        return queryFactory.select(clanAssignedPlayerDTO)
                           .from(clanLeagueAssignedPlayerEntity)
                           .join(playerEntity).on(playerEntity.playerTag.eq(clanLeagueAssignedPlayerEntity.id.playerTag))
                           .leftJoin(clanLeagueAssignedPlayerEntity.clan, clanEntity)
                           .leftJoin(clanEntity.badgeUrl, clanBadgeEntity)
                           .where(clanLeagueAssignedPlayerEntity.id.seasonDate.eq(seasonDate))
                           .fetch();
    }
}
