package open.api.coc.clans.clean.infrastructure.capital.persistence.repository;

import static open.api.coc.clans.clean.infrastructure.capital.persistence.entity.QRaidEntity.raidEntity;
import static open.api.coc.clans.clean.infrastructure.capital.persistence.entity.QRaiderEntity.raiderEntity;
import static open.api.coc.clans.clean.infrastructure.player.persistence.entity.QPlayerEntity.playerEntity;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.capital.persistence.dto.RaiderRankingDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaRaiderCustomRepository {

    private final JPAQueryFactory queryFactory;

    public List<RaiderRankingDTO> findAllResourceLootedRankingByStartDateAndPage(LocalDate startDate, Pageable pageable) {

        ConstructorExpression<RaiderRankingDTO> raiderRankingDTO = Projections.constructor(RaiderRankingDTO.class,
                                                                                           raiderEntity.tag.as("tag"),
                                                                                           playerEntity.name.as("name"),
                                                                                           playerEntity.townHallLevel.as("townHallLevel"),
                                                                                           raiderEntity.resourceLooted.as("resourceLooted"));


        return queryFactory.select(raiderRankingDTO)
                           .from(raiderEntity)
                           .join(raiderEntity.raid, raidEntity)
                           .join(playerEntity).on(playerEntity.playerTag.eq(raiderEntity.tag))
                           .where(raidEntity.startDate.eq(startDate))
                           .orderBy(raiderEntity.resourceLooted.desc())
                           .offset(pageable.getOffset())
                           .limit(pageable.getPageSize())
                           .fetch();
    }

    public List<RaiderRankingDTO> findAllResourceLootedAverageRankingByStartDateAndPage(List<LocalDate> startDates, Pageable pageable) {

        ConstructorExpression<RaiderRankingDTO> raiderRankingDTO = Projections.constructor(RaiderRankingDTO.class,
                                                                                           raiderEntity.tag.as("tag"),
                                                                                           raiderEntity.name.max().as("name"),
                                                                                           playerEntity.townHallLevel.as("townHallLevel"),
                                                                                           raiderEntity.resourceLooted.avg().intValue().as("resourceLooted"));

        return queryFactory.select(raiderRankingDTO)
                           .from(raiderEntity)
                           .leftJoin(raiderEntity.raid, raidEntity)
                           .join(playerEntity).on(playerEntity.playerTag.eq(raiderEntity.tag))
                           .where(raidEntity.startDate.in(startDates))
                           .groupBy(raiderEntity.tag)
                           .having(raiderEntity.tag.count().eq((long) startDates.size()))
                           .orderBy(raiderEntity.resourceLooted.avg().intValue().desc())
                           .offset(pageable.getOffset())
                           .limit(pageable.getPageSize())
                           .fetch();
    }

}
