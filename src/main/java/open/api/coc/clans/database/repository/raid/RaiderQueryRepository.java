package open.api.coc.clans.database.repository.raid;

import static open.api.coc.clans.database.entity.raid.QRaidEntity.raidEntity;
import static open.api.coc.clans.database.entity.raid.QRaiderEntity.raiderEntity;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import open.api.coc.clans.domain.ranking.RankingHallOfFameDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class RaiderQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final RaiderRepository raiderRepository;

    public List<RaiderEntity> findByTag(String playerTag, Integer limit) {
        if (!StringUtils.hasText(playerTag))
            throw CustomRuntimeException.create(ExceptionCode.INTERNAL_ERROR, "playerTag can not be empty");

        return raiderRepository.findByTag(playerTag, limit);
    }

    public List<RaiderEntity> findByName(String playerName, Integer limit) {
        if (!StringUtils.hasText(playerName))
            throw CustomRuntimeException.create(ExceptionCode.INTERNAL_ERROR, "playerName can not be empty");

        return raiderRepository.findByName(playerName, limit);
    }

    public List<RankingHallOfFameDTO> findRankingByStartDateAndPage(LocalDate seasonStartDate, Pageable page) {

        ConstructorExpression<RankingHallOfFameDTO> rankingHallOfFameDTO = Projections.constructor(RankingHallOfFameDTO.class,
                                                                                                   raiderEntity.tag.as("tag"),
                                                                                                   raiderEntity.name.as("name"),
                                                                                                   raiderEntity.resourceLooted.as("score"));

        return queryFactory.select(rankingHallOfFameDTO)
                           .from(raiderEntity)
                           .leftJoin(raiderEntity.raid, raidEntity)
                           .where(raidEntity.startDate.eq(seasonStartDate))
                           .orderBy(raiderEntity.resourceLooted.desc())
                           .offset(page.getOffset())
                           .limit(page.getPageSize())
                           .fetch();

    }

    public List<RankingHallOfFameDTO> findAverageRankingByStartDatesAndPage(List<LocalDate> seasonStartDates, PageRequest page) {

        ConstructorExpression<RankingHallOfFameDTO> rankingHallOfFameDTO = Projections.constructor(RankingHallOfFameDTO.class,
                                                                                                   raiderEntity.tag.as("tag"),
                                                                                                   raiderEntity.name.max().as("name"),
                                                                                                   raiderEntity.resourceLooted.avg().intValue().as("score"));

        return queryFactory.select(rankingHallOfFameDTO)
                           .from(raiderEntity)
                           .leftJoin(raiderEntity.raid, raidEntity)
                           .where(raidEntity.startDate.in(seasonStartDates))
                           .groupBy(raiderEntity.tag)
                           .having(raiderEntity.tag.count().eq((long) seasonStartDates.size()))
                           .orderBy(raiderEntity.resourceLooted.avg().intValue().desc())
                           .offset(page.getOffset())
                           .limit(page.getPageSize())
                           .fetch();
    }
}
