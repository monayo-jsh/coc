package open.api.coc.clans.clean.infrastructure.capital.persistence.repository;

import static open.api.coc.clans.clean.infrastructure.capital.persistence.entity.QRaidEntity.raidEntity;
import static open.api.coc.clans.database.entity.raid.QRaiderEntity.raiderEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaRaiderCustomRepository {

    private final JPAQueryFactory queryFactory;

    public List<RaiderEntity> findAllResourceLootedRankingByStartDateAndPage(LocalDate startDate, Pageable pageable) {
        return queryFactory.select(raiderEntity)
                           .from(raiderEntity)
                           .join(raiderEntity.raid, raidEntity)
                           .where(raidEntity.startDate.eq(startDate))
                           .orderBy(raiderEntity.resourceLooted.desc())
                           .offset(pageable.getOffset())
                           .limit(pageable.getPageSize())
                           .fetch();
    }
}
