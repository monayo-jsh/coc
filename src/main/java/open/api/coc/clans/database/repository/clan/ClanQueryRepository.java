package open.api.coc.clans.database.repository.clan;

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
public class ClanQueryRepository {

    private final ClanRepository clanRepository;
    private final JPAQueryFactory queryFactory;

    public List<ClanEntity> findAllActiveClans() {
        BooleanBuilder condition = createSelectClanBaseConditionBuilder();

        return createSelectClanBaseQuery().where(condition)
                                          .orderBy(clanEntity.order.asc())
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

    public Optional<ClanEntity> findById(String clanTag) {
        return clanRepository.findById(clanTag);
    }
}
