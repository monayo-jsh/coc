package open.api.coc.clans.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.raid.ClanCapitalRaidSeasonResponse;
import open.api.coc.clans.domain.raid.query.RaiderScoreQuery;
import open.api.coc.clans.domain.raid.RaidScoreResponse;
import open.api.coc.clans.domain.raid.query.RaiderScoreQueryFactory;
import open.api.coc.clans.domain.ranking.RankingHallOfFame;
import open.api.coc.clans.service.RaidService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "클랜 캐피탈", description = "클랜 캐피탈 관련 기능")
@RestController
@RequiredArgsConstructor
@RequestMapping("/raid")
public class RaidController {

    private final RaidService raidService;

    @Operation(
        summary = "클랜 캐피탈 데이터 수집용 API, version: 1.00, Last Update: 24.08.20",
        description = "클랜 캐피탈 데이터 수집용 API<br/>서버에 저장된 클랜 중 캐피탈 컨텐츠가 활성화된 클랜을 기준으로 데이터 수집"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PostMapping("/collect/seasons")
    public ResponseEntity<String> collectClanCapitalRaidSeason() {
        raidService.collectClanCapitalRaidSeason();
        return ResponseEntity.ok().body("OK");
    }

    @Operation(
        summary = "클랜 캐피탈 조회 API (실시간 연동), version: 1.00, Last Update: 24.08.20",
        description = "클랜 캐피탈 조회 API (실시간 연동)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = ClanCapitalRaidSeasonResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/{clanTag}/seasons")
    public ResponseEntity<ClanCapitalRaidSeasonResponse> getClanCapitalRaidSeasons(@PathVariable String clanTag) {
        ClanCapitalRaidSeasonResponse clanCapitalRaidAttacker = raidService.getClanCapitalRaidSeason(clanTag);
        return ResponseEntity.ok().body(clanCapitalRaidAttacker);
    }

    @Operation(
        summary = "클랜 캐피탈 점수 조회 API, version: 1.00, Last Update: 24.08.20",
        description = "클랜 캐피탈 점수 조회 API<br>사용자 태그 기준으로 서버에 저장된 데이터를 기반으로 최대 4주간 점수 제공"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = RaidScoreResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/score")
    public ResponseEntity<List<RaidScoreResponse>> getPlayerRaidScoreWithTag(@RequestParam(required = false) String playerTag,
                                                                             @RequestParam(required = false) String playerName) {

        RaiderScoreQuery query = RaiderScoreQueryFactory.create(playerTag, playerName);

        List<RaidScoreResponse> playerRaidScores = raidService.getPlayerRaidScore(query);

        return ResponseEntity.ok().body(playerRaidScores);
    }

    @GetMapping("/attack/season")
    public ResponseEntity<List<RaidScoreResponse>> getAttackCurrentSeason() {
        List<RaidScoreResponse> playerRaidScores = raidService.getAttackCurrentSeason();
        return ResponseEntity.ok().body(playerRaidScores);
    }


    @GetMapping("/ranking/current/season")
    public ResponseEntity<List<RankingHallOfFame>> rankingCurrentSeason() {
        List<RankingHallOfFame> rankingCurrentSeasons = raidService.getRankingCurrentSeason();
        return ResponseEntity.ok().body(rankingCurrentSeasons);
    }

    @GetMapping("/ranking/average/season")
    public ResponseEntity<List<RankingHallOfFame>> rankingAverageSeason() {
        List<RankingHallOfFame> rankingCurrentSeasons = raidService.getRankingAverageSeason();
        return ResponseEntity.ok().body(rankingCurrentSeasons);
    }
}
