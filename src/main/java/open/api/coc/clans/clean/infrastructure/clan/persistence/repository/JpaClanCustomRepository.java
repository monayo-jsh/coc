package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import static open.api.coc.clans.database.entity.clan.QClanBadgeEntity.clanBadgeEntity;
import static open.api.coc.clans.database.entity.clan.QClanContentEntity.clanContentEntity;
import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.common.YnType;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaClanCustomRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<ClanEntity> findById(String tag) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(clanEntity.tag.eq(tag));

        ClanEntity clanEntity = createSelectClanBaseQuery().where(condition)
                                                            .fetchOne();
        return Optional.ofNullable(clanEntity);
    }

    public List<ClanEntity> findByIds(List<String> clanTags) {
        BooleanBuilder condition = createSelectClanBaseConditionBuilder();
        condition.and(clanEntity.tag.in(clanTags));

        return createSelectClanBaseQuery().where(condition)
                                          .fetch();
    }

    public List<ClanEntity> findAllActiveCapitalClans() {
        BooleanBuilder condition = createSelectClanBaseConditionBuilder();
        condition.and(clanContentEntity.clanCapitalYn.eq(YnType.Y.name()));

        return createSelectClanBaseQuery().where(condition)
                                          .orderBy(clanEntity.order.asc())
                                          .fetch();
    }

    private JPAQuery<ClanEntity> createSelectClanBaseQuery() {
        return queryFactory.select(clanEntity)
                           .from(clanEntity)
                           .join(clanEntity.clanContent, clanContentEntity).fetchJoin()
                           .leftJoin(clanEntity.badgeUrl, clanBadgeEntity).fetchJoin();
    }

    private BooleanBuilder createSelectClanBaseConditionBuilder() {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(clanEntity.visibleYn.eq(YnType.Y));
        return builder;
    }

    public Integer selectMaxOrder() {
        return queryFactory.select(clanEntity.order.max())
                           .from(clanEntity)
                           .fetchOne();
    }
}
