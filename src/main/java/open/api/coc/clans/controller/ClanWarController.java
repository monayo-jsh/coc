package open.api.coc.clans.controller;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.ClanWarMissingAttackPlayer;
import open.api.coc.clans.domain.clans.ClanWarResponse;
import open.api.coc.clans.domain.ranking.RankingHallOfFame;
import open.api.coc.clans.service.ClanWarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clan/war")
public class ClanWarController {

    private final ClanWarService clanWarService;

    @GetMapping("/period/{searchMonth}")
    public ResponseEntity<List<ClanWarResponse>> getClanWars(@PathVariable LocalDate searchMonth) {
        return ResponseEntity.ok()
                             .body(clanWarService.getClanWars(searchMonth));
    }

    @GetMapping("/missing/attack/period/{searchMonth}")
    public ResponseEntity<List<ClanWarMissingAttackPlayer>> getClanWarMissingAttackPlayers(@PathVariable LocalDate searchMonth) {
        return ResponseEntity.ok()
                             .body(clanWarService.getClanWarMissingAttackPlayers(searchMonth));
    }

    @GetMapping("{warId}")
    public ResponseEntity<ClanWarResponse> getClanWarDetail(@PathVariable Long warId) {
        return ResponseEntity.ok()
                             .body(clanWarService.getClanWar(warId));
    }

    @GetMapping("/ranking/stars")
    public ResponseEntity<List<RankingHallOfFame>> getRankingClanWarStars(@RequestParam LocalDate searchMonth,
                                                                          @RequestParam String clanTag) {
        return ResponseEntity.ok()
                             .body(clanWarService.getRankingClanWarStars(searchMonth, clanTag));
    }

    @GetMapping("/league/ranking/stars")
    public ResponseEntity<List<RankingHallOfFame>> getRankingLeagueClanWarStars(@RequestParam LocalDate searchMonth,
                                                                                @RequestParam String clanTag) {
        return ResponseEntity.ok()
                             .body(clanWarService.getRankingLeagueClanWarStars(searchMonth, clanTag));
    }
}
