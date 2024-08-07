package open.api.coc.clans.database.repository.clan;

import static open.api.coc.clans.database.entity.clan.QClanBadgeEntity.clanBadgeEntity;
import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarEntity.clanWarEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarMemberAttackEntity.clanWarMemberAttackEntity;
import static open.api.coc.clans.database.entity.clan.QClanWarMemberEntity.clanWarMemberEntity;
import static open.api.coc.clans.database.entity.player.QPlayerEntity.playerEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
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
import open.api.coc.clans.database.entity.clan.ClanWarRecordDTO;
import open.api.coc.clans.database.entity.clan.ClanWarType;
import open.api.coc.clans.database.repository.clan.condition.ClanWarMissingAttackPlayerConditionBuilder;
import open.api.coc.clans.database.repository.clan.condition.ClanWarRecordConditionBuilder;
import open.api.coc.clans.domain.clans.ClanWarMissingAttackPlayerDTO;
import open.api.coc.clans.domain.ranking.ClanWarCountDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanWarQueryRepository {

    private final ClanWarRepository clanWarRepository;
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
            clanEntity.order.as("clanOrder"),
            clanWarEntity.warId.as("warId"),
            clanWarEntity.type.as("warType"),
            clanWarEntity.state.as("warState"),
            clanWarEntity.startTime.as("startTime"),
            clanWarEntity.endTime.as("endTime"),
            clanWarMemberEntity.id.tag.as("playerTag"),
            clanWarMemberEntity.name.as("playerName"),
            clanWarMemberEntity.necessaryAttackYn.as("necessaryAttackYn")
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

    public List<ClanWarRecordDTO> findClanWarRecordsByClanWarTypeAndStartTimePeriod(ClanWarType warType, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        ClanWarRecordConditionBuilder builder = new ClanWarRecordConditionBuilder(warType, from, to);
        return fetchClanWarRecords(pageable, builder);
    }

    public List<ClanWarRecordDTO> findClanWarRecordsByClanTagAndClanWarTypeAndStartTimePeriod(String clanTag, ClanWarType warType, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        ClanWarRecordConditionBuilder builder = new ClanWarRecordConditionBuilder(warType, from, to);
        builder.withClanTag(clanTag);
        return fetchClanWarRecords(pageable, builder);
    }

    private List<ClanWarRecordDTO> fetchClanWarRecords(Pageable pageable, ClanWarRecordConditionBuilder builder) {
        BooleanBuilder condition = builder.build();
        JPAQuery<ClanWarRecordDTO> clanWarRecordQuery = buildClanWarRecord(condition, pageable);

        return clanWarRecordQuery.fetch();
    }

    private JPAQuery<ClanWarRecordDTO> buildClanWarRecord(BooleanBuilder condition, Pageable pageable) {
        ConstructorExpression<ClanWarRecordDTO> clanWarRecordDTO = Projections.constructor(
            ClanWarRecordDTO.class,
            clanEntity.tag.max().as("clanTag"),
            clanEntity.name.max().as("clanName"),
            playerEntity.playerTag.max().as("tag"),
            playerEntity.name.max().as("name"),
            clanWarMemberAttackEntity.id.tag.count().as("attackCount"),
            clanWarMemberAttackEntity.destructionPercentage.sum().coalesce(0).as("totalDestructionPercentage"),
            clanWarMemberAttackEntity.duration.avg().coalesce(0.0).as("avgDuration"),
            clanWarMemberAttackEntity.stars.sum().coalesce(0).as("totalStars"),
            getStartSumByStarCount(3, "threeStars"),
            getStartSumByStarCount(2, "twoStars"),
            getStartSumByStarCount(1, "oneStars"),
            getStartSumByStarCount(0, "zeroStars")
        );

        JPAQuery<ClanWarRecordDTO> query = queryFactory.select(clanWarRecordDTO)
                                                                          .from(clanWarEntity)
                                                                          .join(clanWarEntity.members, clanWarMemberEntity)
                                                                          .leftJoin(clanWarMemberEntity.attacks, clanWarMemberAttackEntity)
                                                                          .join(playerEntity)
                                                                          .on(playerEntity.playerTag.eq(clanWarMemberEntity.id.tag))
                                                                          .join(clanEntity)
                                                                          .on(clanEntity.tag.eq(clanWarEntity.clanTag))
                                                                          .where(condition)
                                                                          .groupBy(clanWarMemberAttackEntity.id.tag)
                                                                          .orderBy(clanWarMemberAttackEntity.stars.sum().desc(),
                                                                                   clanWarMemberAttackEntity.destructionPercentage.sum().desc(),
                                                                                   clanWarMemberAttackEntity.duration.avg().asc());

        if (pageable != null && pageable.isPaged()) {
            query.offset(pageable.getOffset())
                 .limit(pageable.getPageSize());
        }

        return query;
    }

    private NumberExpression<Integer> getStartSumByStarCount(Integer starCount, String alias) {
        return new CaseBuilder().when(clanWarMemberAttackEntity.stars.eq(starCount))
                                .then(1)
                                .otherwise(0)
                                .sum()
                                .as(alias);
    }


    public Map<String, Long> findClanWarCountByClanWarTypeAndStartTimePeriod(ClanWarType warType, LocalDateTime from, LocalDateTime to) {

        ConstructorExpression<ClanWarCountDTO> clanWarCountDTO = Projections.constructor(
            ClanWarCountDTO.class,
            clanWarEntity.clanTag.as("clanTag"),
            clanWarEntity.clanTag.count().as("warCount")
        );

        BooleanBuilder condition = new BooleanBuilder();
        condition.and(clanWarEntity.type.eq(warType))
                 .and(clanWarEntity.startTime.between(from, to));

        List<ClanWarCountDTO> results = queryFactory.select(clanWarCountDTO)
                                                    .from(clanWarEntity)
                                                    .where(condition)
                                                    .groupBy(clanWarEntity.clanTag)
                                                    .fetch();

        return results.stream()
                      .collect(Collectors.toMap(
                          ClanWarCountDTO::clanTag,
                          ClanWarCountDTO::warCount
                      ));
    }

    public Optional<ClanWarEntity> findByClanTagAndStartTime(String clanTag, LocalDateTime startTime) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(clanWarEntity.clanTag.eq(clanTag))
                 .and(clanWarEntity.startTime.eq(startTime));

        return Optional.ofNullable(queryFactory.selectFrom(clanWarEntity)
                                               .where(condition)
                                               .fetchOne());
    }

    public List<ClanWarEntity> findAllByEndTimeAfterAndStateNot(LocalDateTime endTime, String state) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(clanWarEntity.state.ne(state))
                 .and(clanWarEntity.endTime.lt(endTime));

        return queryFactory.selectFrom(clanWarEntity)
                           .where(condition)
                           .fetch();
    }

    public ClanWarEntity save(ClanWarEntity clanWarEntity) {
        return clanWarRepository.save(clanWarEntity);
    }
}
