package open.api.coc.clans.database.repository.clan;

import static open.api.coc.clans.database.entity.clan.QClanBadgeEntity.clanBadgeEntity;
import static open.api.coc.clans.database.entity.clan.QClanContentEntity.clanContentEntity;
import static open.api.coc.clans.database.entity.clan.QClanEntity.clanEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.common.YnType;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ClanEntity> findAllCapitalClan() {
        BooleanBuilder builder = createSelectClanBaseConditionBuilder();

        return queryFactory.select(clanEntity)
                           .from(clanEntity)
                           .join(clanEntity.clanContent, clanContentEntity).fetchJoin()
                           .leftJoin(clanEntity.badgeUrl, clanBadgeEntity).fetchJoin()
                           .where(builder)
                           .orderBy(clanEntity.order.asc())
                           .fetch();
    }

    private BooleanBuilder createSelectClanBaseConditionBuilder() {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(clanEntity.visibleYn.stringValue().eq(YnType.Y.name()))
               .and(clanContentEntity.clanCapitalYn.eq(YnType.Y.name()));

        return builder;
    }

}
