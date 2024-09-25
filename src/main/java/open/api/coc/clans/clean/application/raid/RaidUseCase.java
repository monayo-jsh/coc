package open.api.coc.clans.clean.application.raid;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.raid.mapper.RaidUseCaseMapper;
import open.api.coc.clans.clean.domain.capital.external.client.ClanCapitalClient;
import open.api.coc.clans.clean.domain.capital.external.model.ClanCapitalRaidSeason;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaid;
import open.api.coc.clans.clean.domain.capital.service.ClanCapitalService;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.service.ClanService;
import open.api.coc.clans.clean.presentation.raid.dto.ClanCapitalRaidResponse;
import open.api.coc.clans.clean.presentation.raid.dto.ClanCapitalRaidScoreResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RaidUseCase {

    private final ClanCapitalClient clanCapitalClient;
    private final ClanCapitalService clanCapitalService;

    private final ClanService clanService;

    private final RaidUseCaseMapper raidUseCaseMapper;

    @Transactional
    public ClanCapitalRaidResponse getClanCapitalCurrentSeason(String clanTag) {
        // 1. 클랜 캐피탈 현재 시즌을 조회한다.
        ClanCapitalRaidSeason currentSeason = clanCapitalClient.findCurrentSeasonByClanTag(clanTag);

        // 2. 클랜 캐피탈 조회
        ClanCapitalRaid clanCapitalRaid = clanCapitalService.findByClanTagAndStartDate(clanTag, currentSeason.getStartTime())
                                                            .map(existingRaid -> updateClanCapitalRaid(existingRaid, currentSeason))
                                                            .orElseGet(() -> createClanCapitalRaid(clanTag, currentSeason));

        // 3. 클랜 캐피탈 참가자 정보 갱신
        clanCapitalRaid.updateParticipants(currentSeason.getMembers());

        // 4. 클랜 캐피탈 참가자 데이터 저장
        ClanCapitalRaid updateClanCapitalRaid = clanCapitalService.update(clanCapitalRaid);

        // 5. 클랜 캐피탈 신규 참가자 아이디 매핑
        clanCapitalRaid.mappingParticipantIds(updateClanCapitalRaid.getMembers());

        // 6.응답
        return raidUseCaseMapper.toResponse(clanCapitalRaid);
    }

    public ClanCapitalRaid createClanCapitalRaid(String clanTag, ClanCapitalRaidSeason currentSeason) {
        // 새로운 클랜 캐피탈 생성
        ClanCapitalRaid newClanCapitalRaid = ClanCapitalRaid.createNew(clanTag,
                                                                       currentSeason.getState(),
                                                                       currentSeason.getStartTime(),
                                                                       currentSeason.getEndTime());

        return clanCapitalService.create(newClanCapitalRaid);
    }

    public ClanCapitalRaid updateClanCapitalRaid(ClanCapitalRaid existingRaid, ClanCapitalRaidSeason currentSeason) {
        // 저장된 클랜 캐피탈 데이터 상태 비교 후 업데이트
        if (existingRaid.isDifferentState(currentSeason.getState())) {
            existingRaid.changeState(currentSeason.getState());
            clanCapitalService.updateRaid(existingRaid);
        }

        return existingRaid;
    }

    @Transactional(readOnly = true)
    public List<ClanCapitalRaidScoreResponse> getCurrentSeasonCapitalAttacks() {
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

}
