package open.api.coc.clans.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.raid.RaidScoreResponse;
import open.api.coc.clans.service.RaidService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/raid")
public class RaidController {

    private final RaidService raidService;

    @GetMapping("/score/{playerTag}")
    public ResponseEntity<List<RaidScoreResponse>> getPlayerRaidScore(@PathVariable String playerTag) {

        List<RaidScoreResponse> playerRaidScores = raidService.getPlayerRaidScore(playerTag);

        return ResponseEntity.ok().body(playerRaidScores);
    }

}
