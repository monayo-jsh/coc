package open.api.coc.clans.clean.application.raid;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.application.raid.mapper.RaidScoreQuery;
import open.api.coc.clans.clean.application.raid.mapper.RaidUseCaseMapper;
import open.api.coc.clans.clean.domain.capital.external.client.ClanCapitalClient;
import open.api.coc.clans.clean.domain.capital.external.model.ClanCapitalRaidSeason;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaid;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaidMember;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaidMemberRankingDTO;
import open.api.coc.clans.clean.domain.capital.service.ClanCapitalMemberService;
import open.api.coc.clans.clean.domain.capital.service.ClanCapitalService;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.service.ClanService;
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

    private final ClanService clanService;

    private final RaidUseCaseMapper raidUseCaseMapper;

    @Transactional
    public ClanCapitalRaidResponse getClanCapitalCurrentSeason(String clanTag) {
        // 1. 클랜 캐피탈 현재 시즌을 COC API 조회한다.
        ClanCapitalRaidSeason currentSeason = clanCapitalClient.findCurrentSeasonByClanTag(clanTag);

        // 2. 클랜 캐피탈 도메인 동기화
        ClanCapitalRaid clanCapitalRaid = processClanCapitalCurrentSeason(clanTag, currentSeason);

        // 3.응답
        return raidUseCaseMapper.toResponse(clanCapitalRaid);
    }

    @Transactional(readOnly = true)
    public List<ClanCapitalRaidScoreResponse> getClanCapitalCurrentSeasonAttacks() {
        // 현재 서버에 수집된 최근 시즌 날짜를 조회한다.
        LocalDate latestStartDate = clanCapitalService.findLatestStartDate();

        // 클랜 캐피탈 목록(참여자 목록 포함)을 조회한다.
        List<ClanCapitalRaid> clanCapitalRaids = clanCapitalService.findByStartDate(latestStartDate);

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
        // 캐피탈 컨텐츠 활성화 클랜 목록을 조회한다.
        List<Clan> clans = clanService.findAllActiveCapitalClans();

        for(Clan clan : clans) {
            try {
                // 클랜 캐피탈 현재 시즌을 조회한다.
                ClanCapitalRaidSeason currentSeason = clanCapitalClient.findCurrentSeasonByClanTag(clan.getTag());
                // 클랜 캐피탈 수집
                processClanCapitalCurrentSeason(clan.getTag(), currentSeason);
            } catch (Exception e) {
                log.error("[%s] 클랜 캐피탈 현재 시즌 수집 실패".formatted(clan.getName()), e);
            }
        }

    }

    @Transactional
    public ClanCapitalRaid processClanCapitalCurrentSeason(String tag, ClanCapitalRaidSeason currentSeason) {
        // 클랜 캐피탈 조회 및 생성, 업데이트
        ClanCapitalRaid clanCapitalRaid = clanCapitalService.findByClanTagAndStartDate(tag, currentSeason.getStartTime())
                                                            .map(existingRaid -> clanCapitalService.updateClanCapitalRaid(existingRaid, currentSeason))
                                                            .orElseGet(() -> clanCapitalService.createClanCapitalRaid(tag, currentSeason));

        // 클랜 캐피탈 참가자 정보 갱신
        clanCapitalRaid.updateParticipants(currentSeason.getMembers());

        // 클랜 캐피탈 참가자 데이터 업데이트 및 갱신된 객체 반환
        ClanCapitalRaid mergeClanCapitalRaid = clanCapitalService.updateWithMember(clanCapitalRaid);

        // 신규 참가자 아이디 매핑
        clanCapitalRaid.mappingParticipantIds(mergeClanCapitalRaid.getMembers());

        // 반환
        return clanCapitalRaid;
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
        List<LocalDate> latestStartDates = clanCapitalService.findStartDates(searchCountOfRecent);

        // 캐피탈 참여자 획득 점수 평균 랭킹을 조회한다.
        List<ClanCapitalRaidMemberRankingDTO> raidMemberRankingDTOs = clanCapitalMemberService.rankingResourceLootedAverageByStartDates(latestStartDates);

        // 응답
        return raidMemberRankingDTOs.stream()
                                     .map(raidUseCaseMapper::toRankingResponse)
                                     .toList();
    }

    @Transactional(readOnly = true)
    public List<ClanCapitalRaidScoreResponse> getClanCapitalRaiderScore(RaidScoreQuery query) {
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
            Clan clan = clanService.findById(needSyncRaid.getClanTag());

            try {
                // 클랜 캐피탈 현재 시즌을 조회한다.
                ClanCapitalRaidSeason currentSeason = clanCapitalClient.findCurrentSeasonByClanTag(clan.getTag());
                // 클랜 캐피탈 수집
                processClanCapitalCurrentSeason(clan.getTag(), currentSeason);

                log.info("[%s] 클랜 캐피탈 현재 시즌 자동 수집 완료".formatted(clan.getName()));
            } catch (Exception e) {
                log.error("[%s] 클랜 캐피탈 현재 시즌 자동 수집 실패".formatted(clan.getName()), e);
            }
        }
    }
}
