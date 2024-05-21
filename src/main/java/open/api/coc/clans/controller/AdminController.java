package open.api.coc.clans.controller;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.admin.AdminRaidResponse;
import open.api.coc.clans.domain.clans.ClanResponse;
import open.api.coc.clans.facade.AdminFacade;
import open.api.coc.clans.service.RaidService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clan/cms")

public class AdminController {
    private static final int CLAN_CAPITAL_CRITERIA_SCORE = 20000;
    private final AdminFacade adminFacade;

    @GetMapping("/capital")
    public ResponseEntity<AdminRaidResponse> getClansCapital() {
        return ResponseEntity.ok()
                .body(adminFacade.getCapitalRaidClanWithRecentWeekWarningMember(CLAN_CAPITAL_CRITERIA_SCORE));
    }
}
