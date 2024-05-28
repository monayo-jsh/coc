package open.api.coc.clans.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.raid.RaidEntity;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import open.api.coc.clans.database.entity.raid.converter.RaidEntityConverter;
import open.api.coc.clans.database.repository.raid.RaidRepository;
import open.api.coc.clans.database.repository.raid.RaiderRepository;
import open.api.coc.clans.domain.raid.ClanCapitalRaidSeasonResponse;
import open.api.coc.clans.domain.clans.ClanResponse;
import open.api.coc.clans.domain.raid.conveter.ClanCapitalRaidSeasonResponseConverter;
import open.api.coc.clans.domain.clans.converter.TimeConverter;
import open.api.coc.clans.domain.raid.RaidScoreResponse;
import open.api.coc.clans.domain.raid.conveter.RaidScoreResponseConverter;
import open.api.coc.external.coc.clan.ClanApiService;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeason;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeasons;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RaidService {

    private final ClanApiService clanApiService;
    private final ClansService clansService;

    private final RaidRepository raidRepository;
    private final RaiderRepository raiderRepository;

    private final TimeConverter timeConverter;

    private final RaidEntityConverter raidEntityConverter;

    private final RaidScoreResponseConverter raidScoreResponseConverter;
    private final ClanCapitalRaidSeasonResponseConverter clanCapitalRaidSeasonResponseConverter;

    public void collectClanCapitalRaidSeason() {
        List<ClanResponse> clanList = clansService.getClanCaptialList();
        for (ClanResponse clan : clanList) {
            ClanCapitalRaidSeasonResponse clanCapitalRaidAttacker = findClanCapitalRaidSeason(clan.getTag());
            mergeRaidResult(clan.getTag(), clanCapitalRaidAttacker);
        }
    }

    @Transactional
    public void mergeRaidResult(String clanTag, ClanCapitalRaidSeasonResponse clanCapitalRaidAttacker) {
        Optional<RaidEntity> findRaidEntity = raidRepository.findByClanTagAndStartDate(clanTag, timeConverter.toLocalDate(clanCapitalRaidAttacker.getStartTime()));

        RaidEntity realRaidEntity = raidEntityConverter.convert(clanTag, clanCapitalRaidAttacker);
        if (findRaidEntity.isEmpty()) {
            raidRepository.save(realRaidEntity);
            return;
        }

        RaidEntity raidEntity = findRaidEntity.get();
        List<RaiderEntity> raiderEntities = raidEntity.getRaiderEntityList();

        // DB 데이터
        Map<String, RaiderEntity> dbRaiderMap = makeRadierListToMap(raiderEntities);
        // API 응답 데이터
        Map<String, RaiderEntity> realRaiderMap = makeRadierListToMap(realRaidEntity.getRaiderEntityList());

        for (String playerTag : realRaiderMap.keySet()) {
            RaiderEntity dbRaiderEntity = dbRaiderMap.get(playerTag);
            RaiderEntity realRaiderEntity = realRaiderMap.get(playerTag);
            if (Objects.isNull(dbRaiderEntity)) {
                // 저장되지 않은 기록은 추가
                raiderEntities.add(realRaiderEntity);
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
        List<RaidEntity> raidEntityList = raidRepository.getLastWeekRaidStatistics(PageRequest.of(0,capitalClanCount));
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

    public List<RaidScoreResponse> getPlayerRaidScore(String playerTag) {

        List<RaiderEntity> radierEntities = raiderRepository.findByTag(playerTag);

        for(RaiderEntity raiderEntity : radierEntities) {
            ClanEntity clanEntity = clansService.findClanEntityBy(raiderEntity.getRaid().getClanTag()).orElse(null);
            raiderEntity.getRaid().changeClan(clanEntity);
        }

        return radierEntities.stream()
                             .map(raidScoreResponseConverter::convert)
                             .sorted(Comparator.comparing(RaidScoreResponse::getSeasonStartDate).reversed())
                             .collect(Collectors.toList());

    }

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


}
