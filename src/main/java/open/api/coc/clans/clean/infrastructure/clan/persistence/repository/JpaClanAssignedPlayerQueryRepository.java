package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import static open.api.coc.clans.database.entity.clan.QClanAssignedPlayerEntity.clanAssignedPlayerEntity;
import static open.api.coc.clans.database.entity.clan.QClanBadgeEntity.clanBadgeEntity;
import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
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
public class JpaClanAssignedPlayerQueryRepository {

    private final JPAQueryFactory queryFactory;

    private static final DateTimeFormatter SEASON_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    public String findLatestAssignedDate() {
        String maxSeasonDate = queryFactory.select(clanAssignedPlayerEntity.id.seasonDate.max())
                                           .from(clanAssignedPlayerEntity)
                                           .fetchOne();

        return Optional.ofNullable(maxSeasonDate)
                       .orElseGet(() -> LocalDate.now().format(SEASON_DATE_FORMATTER));
    }

    public List<ClanAssignedPlayerEntity> findAllBySeasonDate(String seasonDate) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(clanAssignedPlayerEntity.id.seasonDate.eq(seasonDate));

        return createBaseQueryWithClan().where(condition)
                                        .fetch();

    }

    public long deleteBySeasonDateAndPlayerTags(String seasonDate, List<String> playerTags) {
        return queryFactory.delete(clanAssignedPlayerEntity)
                           .where(clanAssignedPlayerEntity.id.seasonDate.eq(seasonDate)
                                                                        .and(clanAssignedPlayerEntity.id.playerTag.in(playerTags)))
                           .execute();

    }

    public List<ClanAssignedPlayerEntity> findAllByAssignedDateAndClanTag(String assignedDate, String clanTag) {
        BooleanBuilder condition = ClanAssignedPlayerQueryBuilder.create(assignedDate, clanTag).build();

        return createBaseQueryWithClan().where(condition)
                                        .fetch();
    }

    private JPAQuery<ClanAssignedPlayerEntity> createBaseQueryWithClan() {
        return queryFactory.select(clanAssignedPlayerEntity)
                           .from(clanAssignedPlayerEntity)
                           .leftJoin(clanAssignedPlayerEntity.clan, clanEntity).fetchJoin()
                           .leftJoin(clanEntity.badgeUrl, clanBadgeEntity).fetchJoin();
    }
}
