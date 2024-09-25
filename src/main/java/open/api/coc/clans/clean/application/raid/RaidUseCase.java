package open.api.coc.clans.clean.application.raid;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.raid.mapper.RaidUseCaseMapper;
import open.api.coc.clans.clean.domain.capital.external.client.ClanCapitalClient;
import open.api.coc.clans.clean.domain.capital.external.model.ClanCapitalRaidSeason;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaid;
import open.api.coc.clans.clean.domain.capital.service.ClanCapitalService;
import open.api.coc.clans.clean.presentation.raid.dto.ClanCapitalRaidResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RaidUseCase {

    private final ClanCapitalClient clanCapitalClient;
    private final ClanCapitalService clanCapitalService;

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
}
