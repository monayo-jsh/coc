package open.api.coc.clans.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.config.HallOfFameConfig;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.raid.RaidEntity;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import open.api.coc.clans.database.entity.raid.converter.RaidEntityConverter;
import open.api.coc.clans.database.repository.raid.RaidRepository;
import open.api.coc.clans.database.repository.raid.RaidQueryRepository;
import open.api.coc.clans.database.repository.raid.RaiderRepository;
import open.api.coc.clans.domain.clans.ClanResponse;
import open.api.coc.clans.domain.clans.converter.TimeConverter;
import open.api.coc.clans.domain.raid.ClanCapitalRaidSeasonResponse;
import open.api.coc.clans.domain.raid.RaidScoreResponse;
import open.api.coc.clans.domain.raid.conveter.ClanCapitalRaidSeasonResponseConverter;
import open.api.coc.clans.domain.raid.conveter.RaidScoreResponseConverter;
import open.api.coc.clans.domain.ranking.RankingHallOfFame;
import open.api.coc.external.coc.clan.ClanApiService;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeason;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeasons;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RaidService {

    private final HallOfFameConfig hallOfFameConfig;

    private final ClanApiService clanApiService;
    private final ClansService clansService;

    private final RaidRepository raidRepository;
    private final RaiderRepository raiderRepository;

    private final RaidQueryRepository raiderQueryRepository;

    private final TimeConverter timeConverter;

    private final RaidEntityConverter raidEntityConverter;

    private final RaidScoreResponseConverter raidScoreResponseConverter;
    private final ClanCapitalRaidSeasonResponseConverter clanCapitalRaidSeasonResponseConverter;

    @Transactional
    public void collectClanCapitalRaidSeason() {
        List<ClanResponse> clanList = clansService.getClanCaptialList();
        for (ClanResponse clan : clanList) {
            ClanCapitalRaidSeasonResponse clanCapitalRaidAttacker = findClanCapitalRaidSeason(clan.getTag());
            mergeRaidResult(clan.getTag(), clanCapitalRaidAttacker);
        }
    }

    public void mergeRaidResult(String clanTag, ClanCapitalRaidSeasonResponse clanCapitalRaidAttacker) {
        Optional<RaidEntity> findRaidEntity = raidRepository.findByClanTagAndStartDate(clanTag, timeConverter.toLocalDate(clanCapitalRaidAttacker.getStartTime()));

        RaidEntity realRaidEntity = raidEntityConverter.convert(clanTag, clanCapitalRaidAttacker);
        if (findRaidEntity.isEmpty()) {
            raidRepository.save(realRaidEntity);
            return;
        }

        RaidEntity raidEntity = findRaidEntity.get();

        // DB 데이터
        Map<String, RaiderEntity> dbRaiderMap = makeRadierListToMap(raidEntity.getRaiderEntityList());
        // API 응답 데이터
        Map<String, RaiderEntity> realRaiderMap = makeRadierListToMap(realRaidEntity.getRaiderEntityList());

        for (String playerTag : realRaiderMap.keySet()) {
            RaiderEntity dbRaiderEntity = dbRaiderMap.get(playerTag);
            RaiderEntity realRaiderEntity = realRaiderMap.get(playerTag);
            if (Objects.isNull(dbRaiderEntity)) {
                // 저장되지 않은 기록은 추가
                raidEntity.addRaider(realRaiderEntity);
                continue;
            }

            dbRaiderEntity.setAttacks(realRaiderEntity.getAttacks());
            dbRaiderEntity.setResourceLooted(realRaiderEntity.getResourceLooted());
        }
    }

    private Map<String, RaiderEntity> makeRadierListToMap(List<RaiderEntity> raiderEntities) {
        return raiderEntities.stream().collect(Collectors.toMap(RaiderEntity::getTag, raiderEntity -> raiderEntity));
    }

    public List<RaiderEntity> getRaiderWithLessThanPoints(int capitalClanCount, int point) {
        List<RaidEntity> raidEntityList =
                raidRepository.getLastWeekRaidStatistics(
                        LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)).with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)),
                        PageRequest.of(0,capitalClanCount));
        List<RaiderEntity> result = new ArrayList<>();
        for (RaidEntity raid : raidEntityList) {
            for (RaiderEntity raider : raid.getRaiderEntityList()) {
                if(raider.getResourceLooted() < point) {
                    result.add(raider);
                }
            }
        }
        return result;
    }

    public List<RaidScoreResponse> getPlayerRaidScoreWithTag(String playerTag) {

        final Integer SEARCH_LIMIT = 4;
        List<RaiderEntity> raiderEntities = raiderRepository.findByTag(playerTag, SEARCH_LIMIT);

        return getRaidScoreResponses(raiderEntities);
    }

    public List<RaidScoreResponse> getPlayerRaidScoreWithName(String playerName) {
        final Integer SEARCH_LIMIT = 4;
        List<RaiderEntity> raiderEntities = raiderRepository.findByName(playerName, SEARCH_LIMIT);

        return getRaidScoreResponses(raiderEntities);
    }

    private List<RaidScoreResponse> getRaidScoreResponses(List<RaiderEntity> raiderEntities) {
        for(RaiderEntity raiderEntity : raiderEntities) {
            ClanEntity clanEntity = clansService.findClanEntityBy(raiderEntity.getRaid().getClanTag()).orElse(null);
            raiderEntity.getRaid().changeClan(clanEntity);
        }

        return convertRaidScoreResponse(raiderEntities);
    }

    private List<RaidScoreResponse> convertRaidScoreResponse(List<RaiderEntity> raiderEntities) {
        return raiderEntities.stream()
                             .map(raidScoreResponseConverter::convert)
                             .sorted(Comparator.comparing(RaidScoreResponse::getTag)
                                               .thenComparing(RaidScoreResponse::getSeasonStartDate)
                                               .reversed())
                             .collect(Collectors.toList());
    }

    @Transactional
    public ClanCapitalRaidSeasonResponse getClanCapitalRaidSeason(String clanTag) {
        ClanCapitalRaidSeasonResponse clanCapitalRaidSeason = findClanCapitalRaidSeason(clanTag);
        mergeRaidResult(clanTag, clanCapitalRaidSeason);
        return clanCapitalRaidSeason;
    }

    public ClanCapitalRaidSeasonResponse findClanCapitalRaidSeason(String clanTag) {
        final int SEARCH_LIMIT = 1;
        ClanCapitalRaidSeasons clanCapitalRaidSeasons = clanApiService.findClanCapitalRaidSeasonsByClanTagAndLimit(clanTag, SEARCH_LIMIT)
                                                                      .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "클랜캐피탈 조회 실패"));

        if (clanCapitalRaidSeasons.isEmpty()) {
            throw CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "클랜캐피탈 조회 실패");
        }

        ClanCapitalRaidSeason clanCapitalRaidSeason = clanCapitalRaidSeasons.getItemWithMembers();

        return clanCapitalRaidSeasonResponseConverter.convert(clanCapitalRaidSeason);
    }

    public List<RankingHallOfFame> getRankingCurrentSeason() {
        LocalDate currentSeason = raidRepository.getCurrentSeason();
        if (Objects.isNull(currentSeason)) {
            return Collections.emptyList();
        }

        return raiderRepository.getRankingByStartDateAndLimit(currentSeason, PageRequest.of(0, hallOfFameConfig.getRanking()));
    }

    public List<RankingHallOfFame> getRankingAverageSeason() {
        List<LocalDate> averageSeasonStartDates = raidRepository.getAverageSeasonByLimit(PageRequest.of(0, hallOfFameConfig.getAverage()));
        if (CollectionUtils.isEmpty(averageSeasonStartDates)) {
            return Collections.emptyList();
        }

        return raiderRepository.getRankingByStartDatesAndLimit(averageSeasonStartDates, hallOfFameConfig.getAverage(), PageRequest.of(0, hallOfFameConfig.getRanking()));
    }

    public List<RaidScoreResponse> getAttackCurrentSeason() {
        LocalDate currentSeason = raidRepository.getCurrentSeason();
        if (Objects.isNull(currentSeason)) {
            return Collections.emptyList();
        }

        List<RaidEntity> raidEntities = raiderQueryRepository.findAllByStartDate(currentSeason);

        return raidEntities.stream()
                           .map(raid -> convertRaidScoreResponse(raid.getRaiderEntityList()))
                           .flatMap(Collection::stream)
                           .toList();
    }
}
