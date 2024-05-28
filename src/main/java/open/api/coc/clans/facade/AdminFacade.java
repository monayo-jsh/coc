package open.api.coc.clans.facade;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.raid.RaidEntity;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import open.api.coc.clans.domain.admin.AdminRaidResponse;
import open.api.coc.clans.domain.clans.ClanResponse;
import open.api.coc.clans.service.ClansService;
import open.api.coc.clans.service.RaidService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminFacade {
    private final ClansService clansService;
    private final RaidService raidService;

    public AdminRaidResponse getCapitalRaidClanWithRecentWeekWarningMember(int point) {
        List<ClanResponse> clanResponses = clansService.getClanCaptialList();
        List<RaiderEntity> raiderEntityList = raidService.getRaiderWithLessThanPoints(clanResponses.size(), point);
        return AdminRaidResponse.toResponse(clanResponses, raiderEntityList);
    }
}
