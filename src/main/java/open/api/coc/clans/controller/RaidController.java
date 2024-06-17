package open.api.coc.clans.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.raid.ClanCapitalRaidSeasonResponse;
import open.api.coc.clans.domain.raid.RaidScoreResponse;
import open.api.coc.clans.service.RaidService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/raid")
public class RaidController {

    private final RaidService raidService;

    @PostMapping("/collect/seasons")
    public ResponseEntity<String> collectClanCapitalRaidSeason() {
        raidService.collectClanCapitalRaidSeason();
        return ResponseEntity.ok().body("OK");
    }

    @GetMapping("/{clanTag}/seasons")
    public ResponseEntity<ClanCapitalRaidSeasonResponse> getClanCapitalRaidSeasons(@PathVariable String clanTag) {
        ClanCapitalRaidSeasonResponse clanCapitalRaidAttacker = raidService.getClanCapitalRaidSeason(clanTag);
        return ResponseEntity.ok().body(clanCapitalRaidAttacker);
    }

    @GetMapping("/score/{playerTag}")
    public ResponseEntity<List<RaidScoreResponse>> getPlayerRaidScoreWithTag(@PathVariable String playerTag) {

        List<RaidScoreResponse> playerRaidScores = raidService.getPlayerRaidScoreWithTag(playerTag);

        return ResponseEntity.ok().body(playerRaidScores);
    }

    @GetMapping("/score/playerName")
    public ResponseEntity<List<RaidScoreResponse>> getPlayerRaidScoreWithName(@RequestParam String playerName) {
        List<RaidScoreResponse> playerRaidScores = raidService.getPlayerRaidScoreWithName(playerName);
        return ResponseEntity.ok().body(playerRaidScores);
    }

    @GetMapping("/ranking/current/season")
    public ResponseEntity<List<RaidScoreResponse>> rankingCurrentSeason() {
        List<RaidScoreResponse> rankingCurrentSeasons = raidService.getRankingCurrentSeason();
        return ResponseEntity.ok().body(rankingCurrentSeasons);
    }
}
