package open.api.coc.clans.clean.infrastructure.capital.persistence.repository;


import static open.api.coc.clans.clean.infrastructure.capital.persistence.entity.QRaidEntity.raidEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
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

}
