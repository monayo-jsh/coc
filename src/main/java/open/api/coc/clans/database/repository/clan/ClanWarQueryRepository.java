package open.api.coc.clans.database.repository.clan;

import static open.api.coc.clans.database.entity.clan.QClanWarEntity.clanWarEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanWarQueryRepository {

    private final ClanWarRepository clanWarRepository;
    private final JPAQueryFactory queryFactory;

    public Optional<ClanWarEntity> findByClanTagAndPreparationStartTime(String clanTag, LocalDateTime preparationStartTime) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(clanWarEntity.clanTag.eq(clanTag))
                 .and(clanWarEntity.preparationStartTime.eq(preparationStartTime));

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

    public Optional<ClanWarEntity> findByClanTagAndWarTag(String clanTag, String warTag) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(clanWarEntity.clanTag.eq(clanTag));
        condition.and(clanWarEntity.warTag.eq(warTag));

        ClanWarEntity clanWar = queryFactory.selectFrom(clanWarEntity)
                                            .where(condition)
                                            .fetchOne();
        return Optional.ofNullable(clanWar);
    }
}
