package open.api.coc.clans.clean.infrastructure.capital.persistence.repository;


import static open.api.coc.clans.clean.infrastructure.capital.persistence.entity.QRaidEntity.raidEntity;
import static open.api.coc.clans.database.entity.raid.QRaiderEntity.raiderEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.capital.persistence.entity.RaidEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaRaidCustomRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<RaidEntity> findByClanTagAndStartDate(String clanTag, LocalDate startDate) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(raidEntity.clanTag.eq(clanTag))
                 .and(raidEntity.startDate.eq(startDate));

        RaidEntity findRaidEntity = queryFactory.select(raidEntity)
                                                .from(raidEntity)
                                                .where(condition)
                                                .fetchOne();

        return Optional.ofNullable(findRaidEntity);
    }

    public void update(RaidEntity entity) {
        queryFactory.update(raidEntity)
                    .set(raidEntity.state, entity.getState())
                    .where(raidEntity.id.eq(entity.getId()))
                    .execute();
    }

    public LocalDate findLatestStartDate() {
        return queryFactory.select(raidEntity.startDate.max())
                           .from(raidEntity)
                           .fetchOne();
    }

    public List<RaidEntity> findAllWithRaiderByStartDate(LocalDate startDate) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(raidEntity.startDate.eq(startDate));

        return queryFactory.select(raidEntity)
                           .from(raidEntity)
                           .join(raidEntity.raiders, raiderEntity).fetchJoin()
                           .where(condition)
                           .fetch();
    }
}
