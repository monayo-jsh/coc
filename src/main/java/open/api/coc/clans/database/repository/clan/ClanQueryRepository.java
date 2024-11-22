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
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(clanEntity.tag.eq(clanTag));

        return Optional.ofNullable(createSelectClanBaseQuery().where(condition)
                                                              .fetchOne());
    }

    public List<ClanEntity> findAllByID(List<String> clanTags) {
        BooleanBuilder condition = createSelectClanBaseConditionBuilder().and(clanEntity.tag.in(clanTags));

        return createSelectClanBaseQuery().where(condition)
                                          .fetch();
    }

    public void saveAll(List<ClanEntity> clanEntities) {
        clanRepository.saveAll(clanEntities);
    }

}
