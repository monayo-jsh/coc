package open.api.coc.clans.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.ClanCapitalAttackerRes;
import open.api.coc.clans.domain.clans.ClanCapitalUnderAttackerRes;
import open.api.coc.clans.domain.clans.ClanCurrentWarRes;
import open.api.coc.clans.domain.clans.ClanMemberListRes;
import open.api.coc.clans.domain.clans.ClanRes;
import open.api.coc.clans.service.ClansService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("clans")
public class ClansController {

    private final ClansService clansService;

    @GetMapping("")
    public ResponseEntity<List<ClanRes>> getClans() {
        return ResponseEntity.ok()
                             .body(clansService.getClanResList());
    }

    @GetMapping("/war")
    public ResponseEntity<List<ClanRes>> getClansWar() {
        return ResponseEntity.ok()
                             .body(clansService.getClanWarResList());
    }

    @GetMapping("{clanTag}")
    public ResponseEntity<?> findClan(@PathVariable String clanTag) {
        Map<String, Object> clan = clansService.findClanByClanTag(clanTag);
        return ResponseEntity.ok().body(clan);
    }

    @GetMapping("{clanTag}/members")
    public ResponseEntity<ClanMemberListRes> getClanMembers(@PathVariable String clanTag) {
        ClanMemberListRes clanMemberList = clansService.findClanMembersByClanTag(clanTag);
        return ResponseEntity.ok().body(clanMemberList);
    }

    @GetMapping("{clanTag}/current/war")
    public ResponseEntity<ClanCurrentWarRes> getClanCurrentWar(@PathVariable String clanTag) {
        ClanCurrentWarRes clanCurrentWar = clansService.getClanCurrentWar(clanTag);
        return ResponseEntity.ok().body(clanCurrentWar);
    }

    @GetMapping("{clanTag}/capitalraidseasons/attack/count")
    public ResponseEntity<ClanCapitalAttackerRes> findClanCapitalRaidSeasons(@PathVariable String clanTag) {
        ClanCapitalAttackerRes clanCapitalRaidAttacker = clansService.findClanCapitalRaidSeasons(clanTag);
        return ResponseEntity.ok().body(clanCapitalRaidAttacker);
    }

    @GetMapping("capital/attack/count")
    public ResponseEntity<List<ClanCapitalAttackerRes> > getCapitalAttackers() throws ExecutionException, InterruptedException {
        List<ClanCapitalAttackerRes> capitalAttackersMap = clansService.getCapitalAttackers();
        return ResponseEntity.ok().body(capitalAttackersMap);
    }

    @GetMapping("capital/under/attacker")
    public ResponseEntity<List<ClanCapitalUnderAttackerRes>> getCapitalAttackerMissing() throws ExecutionException, InterruptedException {
        List<ClanCapitalUnderAttackerRes> capitalMissingAttackers = clansService.getCapitalMissingAttackers();
        return ResponseEntity.ok().body(capitalMissingAttackers);
    }
}
