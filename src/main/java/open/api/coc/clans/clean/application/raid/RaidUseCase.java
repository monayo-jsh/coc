package open.api.coc.clans.clean.application.raid;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.application.raid.mapper.RaidUseCaseMapper;
import open.api.coc.clans.clean.domain.capital.external.client.ClanCapitalClient;
import open.api.coc.clans.clean.domain.capital.external.model.ClanCapitalRaidSeason;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaid;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaidMemberRankingDTO;
import open.api.coc.clans.clean.domain.capital.service.ClanCapitalMemberService;
import open.api.coc.clans.clean.domain.capital.service.ClanCapitalService;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.service.ClanService;
import open.api.coc.clans.clean.presentation.common.dto.RankingHallOfFameResponse;
import open.api.coc.clans.clean.presentation.raid.dto.ClanCapitalRaidResponse;
import open.api.coc.clans.clean.presentation.raid.dto.ClanCapitalRaidScoreResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RaidUseCase {

    private final ClanCapitalClient clanCapitalClient;

    private final ClanCapitalService clanCapitalService;
    private final ClanCapitalMemberService clanCapitalMemberService;

    private final ClanService clanService;

    private final RaidUseCaseMapper raidUseCaseMapper;

    @Transactional
    public ClanCapitalRaidResponse getClanCapitalCurrentSeason(String clanTag) {
        // 1. 클랜 캐피탈 현재 시즌을 COC API 조회한다.
        ClanCapitalRaidSeason currentSeason = clanCapitalClient.findCurrentSeasonByClanTag(clanTag);

        // 2. 클랜 캐피탈 도메인 조회
        ClanCapitalRaid clanCapitalRaid = processClanCapitalCurrentSeason(clanTag, currentSeason);

        // 3. 신규 참가자 아이디 매핑
        clanCapitalRaid.mappingParticipantIds(clanCapitalRaid.getMembers());

        // 4.응답
        return raidUseCaseMapper.toResponse(clanCapitalRaid);
    }

    @Transactional(readOnly = true)
    public List<ClanCapitalRaidScoreResponse> getClanCapitalCurrentSeasonAttacks() {
        // 현재 서버에 수집된 최근 시즌 날짜를 조회한다.
        LocalDate latestStartDate = clanCapitalService.findLatestStartDate();

        // 클랜 캐피탈 목록(참여자 목록 포함)을 조회한다.
        List<ClanCapitalRaid> clanCapitalRaids = clanCapitalService.findByStartDate(latestStartDate);

        // 클랜 정보를 조회한다.
        Map<String, Clan> clanMap = clanService.findAllMapByIds(clanCapitalRaids.stream().map(ClanCapitalRaid::getClanTag).toList());

        // 응답
        return clanCapitalRaids.stream()
                               .flatMap(raid -> raid.getMembers()
                                                    .stream()
                                                    .map(member -> raidUseCaseMapper.toResponse(member, raid, clanMap.get(raid.getClanTag()))))
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
        return clanCapitalService.update(clanCapitalRaid);
    }

    @Transactional(readOnly = true)
    public List<RankingHallOfFameResponse> getRankingCurrentSeason() {
        // 현재 서버에 수집된 최근 시즌 날짜를 조회한다.
        LocalDate latestStartDate = clanCapitalService.findLatestStartDate();

        // 캐피탈 참여자 획득 점수 랭킹을 조회한다.
        List<ClanCapitalRaidMemberRankingDTO> clanCapitalRaidMembers = clanCapitalMemberService.rankingResourceLootedByStartDate(latestStartDate);

        // 응답
        return clanCapitalRaidMembers.stream()
                                     .map(raidUseCaseMapper::toRankingResponse)
                                     .toList();
    }

    @Transactional(readOnly = true)
    public List<RankingHallOfFameResponse> getRankingAverageSeason() {
        // 현재 서버에 수집된 최근 시즌 날짜 목록을 조회한다.
        List<LocalDate> startDates = clanCapitalService.findAverageStartDates();

        // 캐피탈 참여자 획득 점수 평균 랭킹을 조회한다.
        List<ClanCapitalRaidMemberRankingDTO> clanCapitalRaidMembers = clanCapitalMemberService.rankingResourceLootedAverageByStartDates(startDates);

        // 응답
        return clanCapitalRaidMembers.stream()
                                     .map(raidUseCaseMapper::toRankingResponse)
                                     .toList();
    }
}
