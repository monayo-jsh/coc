package open.api.coc.clans.controller;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.ranking.RankingHallOfFame;
import open.api.coc.clans.service.ClanWarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clan/war")
public class ClanWarController {

    private final ClanWarService clanWarService;

    @GetMapping("ranking")
    public ResponseEntity<List<RankingHallOfFame>> getRankingClanWarStars(LocalDate searchMonth) {
        return ResponseEntity.ok()
                             .body(clanWarService.getRankingClanWarStars(searchMonth));
    }

}
