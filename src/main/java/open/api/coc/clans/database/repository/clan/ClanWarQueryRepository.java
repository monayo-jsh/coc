package open.api.coc.clans.database.repository.clan;

import static open.api.coc.clans.database.entity.clan.QClanBadgeEntity.clanBadgeEntity;
import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarEntity.clanWarEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarMemberAttackEntity.clanWarMemberAttackEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarMemberEntity.clanWarMemberEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
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
import open.api.coc.clans.database.repository.clan.condition.ClanWarMissingAttackPlayerConditionBuilder;
import open.api.coc.clans.domain.clans.ClanWarMissingAttackPlayerDTO;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanWarQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<ClanWarEntity> findByWarId(Long warId) {
        if (warId == null) {
            throw new IllegalArgumentException("warId must not be null");
        }

        ClanWarEntity clanWar = queryFactory.selectFrom(clanWarEntity)
                                            .where(clanWarEntity.warId.eq(warId))
                                            .fetchOne();

        if (clanWar != null) {
            ClanEntity clan = buildClanBaseQuery().where(clanEntity.tag.eq(clanWar.getClanTag())).fetchOne();
            clanWar.changeClan(clan);
        }

        return Optional.ofNullable(clanWar);
    }

    public List<ClanWarEntity> findAllByStartTimePeriod(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Date parameters must not be null");
        }

        List<ClanWarEntity> clanWarEntities = queryFactory.selectFrom(clanWarEntity)
                                                          .where(clanWarEntity.startTime.between(from, to))
                                                          .orderBy(clanWarEntity.warId.asc())
                                                          .fetch();

        if (!clanWarEntities.isEmpty()) {
            Set<String> clanTags = clanWarEntities.stream().map(ClanWarEntity::getClanTag).collect(Collectors.toSet());
            Map<String, ClanEntity> clans = buildClanBaseQuery().where(clanEntity.tag.in(clanTags)).fetch()
                                                                .stream().collect(Collectors.toMap(ClanEntity::getTag, clan -> clan));


            clanWarEntities.forEach(clanWarEntity -> {
                ClanEntity clan = clans.get(clanWarEntity.getClanTag());
                clanWarEntity.changeClan(clan);
            });
        }

        return clanWarEntities;
    }

    private JPAQuery<ClanEntity> buildClanBaseQuery() {
        return queryFactory.selectFrom(clanEntity)
                           .join(clanEntity.badgeUrl, clanBadgeEntity).fetchJoin();
    }

    public List<ClanWarMissingAttackPlayerDTO> findAllMissingAttackPlayerByStartTimePeriod(LocalDateTime from, LocalDateTime to) {
        ClanWarMissingAttackPlayerConditionBuilder builder = new ClanWarMissingAttackPlayerConditionBuilder(from, to);
        BooleanBuilder condition = builder.build();
        return buildMissingAttackPlayerQuery(condition).fetch();
    }

    public List<ClanWarMissingAttackPlayerDTO> findMissingAttackByNameAndStartTimePeriod(String playerName, LocalDateTime from, LocalDateTime to) {
        ClanWarMissingAttackPlayerConditionBuilder builder = new ClanWarMissingAttackPlayerConditionBuilder(from, to);
        builder.withPlayerName(playerName);
        BooleanBuilder condition = builder.build();
        return buildMissingAttackPlayerQuery(condition).fetch();
    }

    public List<ClanWarMissingAttackPlayerDTO> findMissingAttackByTagAndStartTimePeriod(String playerTag, LocalDateTime from, LocalDateTime to) {
        ClanWarMissingAttackPlayerConditionBuilder builder = new ClanWarMissingAttackPlayerConditionBuilder(from, to);
        builder.withPlayerTag(playerTag);
        BooleanBuilder condition = builder.build();
        return buildMissingAttackPlayerQuery(condition).fetch();
    }

    private JPAQuery<ClanWarMissingAttackPlayerDTO> buildMissingAttackPlayerQuery(BooleanBuilder condition) {
        ConstructorExpression<ClanWarMissingAttackPlayerDTO> clanWarMissingAttackPlayerDTO = Projections.constructor(
            ClanWarMissingAttackPlayerDTO.class,
            clanEntity.name.as("clanName"),
            clanWarEntity.warId.as("warId"),
            clanWarEntity.type.as("warType"),
            clanWarEntity.state.as("warState"),
            clanWarEntity.startTime.as("startTime"),
            clanWarMemberEntity.id.tag.as("playerTag"),
            clanWarMemberEntity.name.as("playerName")
        );

        return queryFactory.select(clanWarMissingAttackPlayerDTO)
                           .from(clanWarEntity)
                           .join(clanEntity).on(clanEntity.tag.eq(clanWarEntity.clanTag))
                           .join(clanWarEntity.members, clanWarMemberEntity)
                           .leftJoin(clanWarMemberEntity.attacks, clanWarMemberAttackEntity)
                           .where(condition)
                           .groupBy(clanWarMemberEntity.id.warId, clanWarMemberEntity.id.tag)
                           .having(clanWarMemberAttackEntity.id.order.count()
                                                                     .lt(clanWarEntity.attacksPerMember))
                           .orderBy(clanWarEntity.warId.asc(), clanWarMemberEntity.mapPosition.asc());
    }
}
