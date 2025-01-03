package open.api.coc.clans.clean.application.raid;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.application.raid.query.RaidScoreQuery;
import open.api.coc.clans.clean.application.raid.mapper.RaidUseCaseMapper;
import open.api.coc.clans.clean.application.raid.query.RaidScoreQueryFactory;
import open.api.coc.clans.clean.domain.capital.external.client.ClanCapitalClient;
import open.api.coc.clans.clean.domain.capital.external.model.ClanCapitalRaidSeason;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaid;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaidMember;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaidMemberRankingDTO;
import open.api.coc.clans.clean.domain.capital.service.ClanCapitalMemberService;
import open.api.coc.clans.clean.domain.capital.service.ClanCapitalService;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.model.ClanContentType;
import open.api.coc.clans.clean.domain.clan.service.ClanQueryService;
import open.api.coc.clans.clean.domain.clan.service.ClanRegistrationService;
import open.api.coc.clans.clean.presentation.common.dto.RankingHallOfFameResponse;
import open.api.coc.clans.clean.presentation.raid.dto.ClanCapitalRaidResponse;
import open.api.coc.clans.clean.presentation.raid.dto.ClanCapitalRaidScoreResponse;
import open.api.coc.clans.common.config.HallOfFameConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RaidUseCase {

    private final HallOfFameConfig hallOfFameConfig;
    private final ClanCapitalClient clanCapitalClient;

    private final ClanCapitalService clanCapitalService;
    private final ClanCapitalMemberService clanCapitalMemberService;

    private final ClanRegistrationService clanService;
    private final ClanQueryService clanQueryService;

    private final RaidUseCaseMapper raidUseCaseMapper;

    @Transactional
    public ClanCapitalRaidResponse getClanCapitalCurrentSeason(String clanTag) {
        // 1. 클랜 캐피탈 현재 시즌을 COC API 조회한다.
        ClanCapitalRaidSeason currentSeason = clanCapitalClient.findCurrentSeasonByClanTag(clanTag);

        // 2. 클랜 캐피탈 수집
        ClanCapitalRaid clanCapitalRaid = clanCapitalService.collectCurrentSeason(clanTag, currentSeason);

        // 3.응답
        return raidUseCaseMapper.toClanCapitalRaidResponse(clanCapitalRaid);
    }

    @Transactional(readOnly = true)
    public List<ClanCapitalRaidScoreResponse> getClanCapitalCurrentSeasonAttacks() {
        // 현재 서버에 수집된 최근 시즌 날짜를 조회한다.
        LocalDate latestStartDate = clanCapitalService.findLatestStartDate();

        // 클랜 캐피탈 목록(참여자 목록 포함)을 조회한다.
        List<ClanCapitalRaid> clanCapitalRaids = clanCapitalService.findAllByStartDate(latestStartDate);

        if (clanCapitalRaids.isEmpty()) return Collections.emptyList();

        // 클랜 정보를 조회한다.
        Map<String, Clan> clanMap = clanService.findAllMapByIds(clanCapitalRaids.stream().map(ClanCapitalRaid::getClanTag).toList());

        // 응답
        return clanCapitalRaids.stream()
                               .flatMap(raid -> raid.getMembers()
                                                    .stream()
                                                    .map(member -> toClanCapitalRaidScoreResponse(member, raid, clanMap)))
                               .sorted(Comparator.comparing(ClanCapitalRaidScoreResponse::getClanOrder)
                                                 .thenComparing(ClanCapitalRaidScoreResponse::id))
                               .toList();
    }

    @Transactional
    public void collectClanCapitalCurrentSeason() {
        // 캐피탈 활성화 클랜 목록을 조회한다.
        List<Clan> clans = clanQueryService.findAllActiveContentByClanContentType(ClanContentType.CLAN_CAPITAL);

        for(Clan clan : clans) {
            try {
                // 클랜 캐피탈 현재 시즌을 조회한다.
                ClanCapitalRaidSeason currentSeason = clanCapitalClient.findCurrentSeasonByClanTag(clan.getTag());

                // 클랜 캐피탈 수집
                clanCapitalService.collectCurrentSeason(clan.getTag(), currentSeason);
            } catch (Exception e) {
                log.error("[%s] 클랜 캐피탈 현재 시즌 수집 실패".formatted(clan.getName()), e);
            }
        }

    }

    @Transactional(readOnly = true)
    public List<RankingHallOfFameResponse> getRankingCurrentSeason() {
        // 현재 서버에 수집된 최근 시즌 날짜를 조회한다.
        LocalDate latestStartDate = clanCapitalService.findLatestStartDate();

        // 캐피탈 참여자 획득 점수 랭킹을 조회한다.
        List<ClanCapitalRaidMemberRankingDTO> raidMemberRankingDTOs = clanCapitalMemberService.rankingResourceLootedByStartDate(latestStartDate);

        // 응답
        return raidMemberRankingDTOs.stream()
                                     .map(raidUseCaseMapper::toRankingResponse)
                                     .toList();
    }

    @Transactional(readOnly = true)
    public List<RankingHallOfFameResponse> getRankingAverageSeason() {
        // 현재 서버에 수집된 최근 시작 날짜 목록을 조회한다.
        int searchCountOfRecent = hallOfFameConfig.getAverage() + 1;
        List<LocalDate> latestStartDates = clanCapitalService.findAllLatestStartDateByCount(searchCountOfRecent);

        // 캐피탈 참여자 획득 점수 평균 랭킹을 조회한다.
        List<ClanCapitalRaidMemberRankingDTO> raidMemberRankingDTOs = clanCapitalMemberService.rankingResourceLootedAverageByStartDates(latestStartDates);

        // 응답
        return raidMemberRankingDTOs.stream()
                                     .map(raidUseCaseMapper::toRankingResponse)
                                     .toList();
    }

    @Transactional(readOnly = true)
    public List<ClanCapitalRaidScoreResponse> getClanCapitalRaiderScore(String playerTag, String playerName) {
        // 조회하기 위한 쿼리 생성
        RaidScoreQuery query = RaidScoreQueryFactory.create(playerTag, playerName);

        // 현재 서버에 수집된 캐피탈 참여자의 공격 목록을 조회한다.
        List<ClanCapitalRaidMember> raidMembers = query.getStrategy().execute(clanCapitalMemberService, query.getCriteria(), query.getLimit());

        if (raidMembers.isEmpty()) return Collections.emptyList();

        // 캐피탈 목록을 조회한다.
        Map<Long, ClanCapitalRaid> raidMap = clanCapitalService.findAllMapByIds(raidMembers.stream().map(ClanCapitalRaidMember::getRaidId).toList());

        // 클랜 목록을 조회한다.
        Map<String, Clan> clanMap = clanService.findAllMapByIds(raidMap.values().stream().map(ClanCapitalRaid::getClanTag).toList());

        // 응답
        return raidMembers.stream()
                                     .map(member -> toClanCapitalRaidScoreResponse(member, raidMap, clanMap))
                                     .sorted(Comparator.comparing(ClanCapitalRaidScoreResponse::tag)
                                                       .thenComparing(ClanCapitalRaidScoreResponse::id).reversed())
                                     .toList();
    }

    @Transactional(readOnly = true)
    public List<ClanCapitalRaidScoreResponse> getViolationRaiders() {
        // 수집 시작일 기준 지난주차의 캐피탈 참여 목록 조회한다.
        List<ClanCapitalRaid> raids = clanCapitalService.findAllWithMembersFromLastWeek();

        // 클랜 목록을 조회한다.
        Map<String, Clan> clanMap = clanService.findAllMapByIds(raids.stream().map(ClanCapitalRaid::getClanTag).toList());

        // 캐피탈 참여 위반 목록을 수집해서 응답
        return raids.stream()
                    .flatMap(raid -> raid.getViolationMembers()
                                         .stream().map(member -> toClanCapitalRaidScoreResponse(member, raid, clanMap)))
                    .toList();
    }

    private ClanCapitalRaidScoreResponse toClanCapitalRaidScoreResponse(ClanCapitalRaidMember member, Map<Long, ClanCapitalRaid> raidMap, Map<String, Clan> clanMap) {
        ClanCapitalRaid raid = raidMap.getOrDefault(member.getRaidId(), null);
        return toClanCapitalRaidScoreResponse(member, raid, clanMap);
    }

    private ClanCapitalRaidScoreResponse toClanCapitalRaidScoreResponse(ClanCapitalRaidMember member, ClanCapitalRaid raid, Map<String, Clan> clanMap) {
        Clan clan = null;
        if (raid != null) {
            clan = clanMap.getOrDefault(raid.getClanTag(), null);
        }
        return raidUseCaseMapper.toClanCapitalRaidScoreResponse(member, raid, clan);
    }

    @Transactional
    public void collectCapitalCurrentSeason() {
        // 수집이 필요한 클랜 캐피탈 목록을 조회한다.
        List<ClanCapitalRaid> needSyncRaids = clanCapitalService.findAllThatNeedSync();

        for(ClanCapitalRaid needSyncRaid : needSyncRaids) {
            // 클랜 정보를 조회한다.
            Clan clan = clanService.findById(needSyncRaid.getClanTag());

            try {
                log.info("[%s] 클랜 캐피탈 현재 시즌 자동 수집 시작".formatted(clan.getName()));

                // 클랜 캐피탈 현재 시즌을 조회한다.
                ClanCapitalRaidSeason currentSeason = clanCapitalClient.findCurrentSeasonByClanTag(clan.getTag());

                // 클랜 캐피탈 수집
                ClanCapitalRaid result = clanCapitalService.collectCurrentSeason(clan.getTag(), currentSeason);

                log.info("[%s] 클랜 캐피탈 현재 시즌 자동 수집 결과: %s".formatted(clan.getName(), result.getState()));
            } catch (Exception e) {
                log.error("[%s] 클랜 캐피탈 현재 시즌 자동 수집 실패".formatted(clan.getName()), e);
            }
        }
    }
}
