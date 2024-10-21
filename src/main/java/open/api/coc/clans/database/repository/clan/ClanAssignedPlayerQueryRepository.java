package open.api.coc.clans.database.repository.clan;

import static open.api.coc.clans.database.entity.clan.QClanAssignedPlayerEntity.clanAssignedPlayerEntity;
import static open.api.coc.clans.database.entity.clan.QClanBadgeEntity.clanBadgeEntity;
import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;
import static open.api.coc.clans.database.entity.player.QPlayerEntity.playerEntity;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerDTO;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanAssignedPlayerQueryRepository {

    private final JPAQueryFactory queryFactory;

    private static final DateTimeFormatter SEASON_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    public String findLatestSeasonDate() {
        String maxSeasonDate = queryFactory.select(clanAssignedPlayerEntity.id.seasonDate.max())
                                           .from(clanAssignedPlayerEntity)
                                           .fetchOne();

        return Optional.ofNullable(maxSeasonDate)
                       .orElseGet(() -> LocalDate.now().format(SEASON_DATE_FORMATTER));
    }

    public List<ClanAssignedPlayerDTO> findAllBySeasonDate(String seasonDate) {
        ConstructorExpression<ClanAssignedPlayerDTO> clanAssignedPlayerDTO = Projections.constructor(ClanAssignedPlayerDTO.class,
                                                                                                     clanAssignedPlayerEntity.id.seasonDate.as("seasonDate"),
                                                                                                     clanAssignedPlayerEntity.id.playerTag.as("playerTag"),
                                                                                                     playerEntity.name.as("playerName"),
                                                                                                     clanAssignedPlayerEntity.clan.as("clan"));
        return queryFactory.select(clanAssignedPlayerDTO)
                           .from(clanAssignedPlayerEntity)
                           .join(playerEntity).on(playerEntity.playerTag.eq(clanAssignedPlayerEntity.id.playerTag))
                           .leftJoin(clanAssignedPlayerEntity.clan, clanEntity)
                           .leftJoin(clanEntity.badgeUrl, clanBadgeEntity)
                           .where(clanAssignedPlayerEntity.id.seasonDate.eq(seasonDate))
                           .fetch();
    }

    public long deleteBySeasonDateAndPlayerTags(String seasonDate, List<String> playerTags) {
        return queryFactory.delete(clanAssignedPlayerEntity)
                           .where(clanAssignedPlayerEntity.id.seasonDate.eq(seasonDate)
                                                                        .and(clanAssignedPlayerEntity.id.playerTag.in(playerTags)))
                           .execute();

    }
}
