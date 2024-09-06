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
import open.api.coc.clans.domain.raid.RaidScoreResponse;
import open.api.coc.clans.domain.raid.query.RaiderScoreQuery;
import open.api.coc.clans.domain.raid.query.RaiderScoreQueryFactory;
import open.api.coc.clans.domain.ranking.RankingHallOfFameDTO;
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
@RequestMapping("/api/raid")
public class RaidController {

    private final RaidService raidService;

    @Operation(
        summary = "클랜의 현재 시즌 캐피탈 정보를 조회(실시간 연동)합니다. version: 1.00, Last Update: 24.08.20",
        description = "이 API는 클랜의 현재 시즌 캐피탈 정보를 실시간 연동하여 제공합니다.<br>연동 결과는 서버에 저장합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = ClanCapitalRaidSeasonResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/{clanTag}/seasons/current")
    public ResponseEntity<ClanCapitalRaidSeasonResponse> getClanCapitalRaidSeasons(@PathVariable String clanTag) {
        ClanCapitalRaidSeasonResponse clanCapitalRaidAttacker = raidService.getClanCapitalRaidSeason(clanTag);
        return ResponseEntity.ok().body(clanCapitalRaidAttacker);
    }

    @Operation(
        summary = "현재 시즌 캐피탈 공격 기록 정보를 조회합니다. version: 1.00, Last Update: 24.08.20",
        description = "이 API는 서버에 수집된 현재 시즌 캐피탈 공격 기록 정보를 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = RaidScoreResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/seasons/current/attacks")
    public ResponseEntity<List<RaidScoreResponse>> getAttackCurrentSeason() {
        List<RaidScoreResponse> playerRaidScores = raidService.getAttackCurrentSeason();
        return ResponseEntity.ok().body(playerRaidScores);
    }

    @Operation(
        summary = "캐피탈 활성화 클랜들의 현재 시즌 캐피탈 데이터 수집을 수행합니다., version: 1.00, Last Update: 24.08.20",
        description = "이 API는 캐피탈 활성화 클랜들의 현재 시즌 캐피탈 데이터를 연동 후 서버에 수집합니다,"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PostMapping("/seasons/current/collect")
    public ResponseEntity<String> collectClanCapitalRaidSeason() {
        raidService.collectClanCapitalRaidSeason();
        return ResponseEntity.ok().body("OK");
    }

    @Operation(
        summary = "클랜 캐피탈 현재 시즌 획득 점수 랭킹을 조회합니다. version: 1.00, Last Update: 24.08.20",
        description = "이 API는 서버에 수집된 현재 시즌 획득 점수 데이터를 기반으로 랭킹을 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = RankingHallOfFameDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/seasons/current/ranking")
    public ResponseEntity<List<RankingHallOfFameDTO>> rankingCurrentSeason() {
        return ResponseEntity.ok()
                             .body(raidService.getRankingCurrentSeason());
    }

    @Operation(
        summary = "클랜 캐피탈 평균 점수 랭킹을 조회합니다. version: 1.00, Last Update: 24.08.20",
        description = "이 API는 서버에 수집된 지난 3주간의 획득 점수 데이터를 기반으로 랭킹을 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = RankingHallOfFameDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/seasons/average/ranking")
    public ResponseEntity<List<RankingHallOfFameDTO>> rankingAverageSeason() {
        return ResponseEntity.ok()
                             .body(raidService.getRankingAverageSeason());
    }

    @Operation(
        summary = "플레어이의 캐피탈 점수를 조회합니다. version: 1.00, Last Update: 24.08.20",
        description = "이 API는 서버에 수집된 데이터를 기반으로 최근 4주간의 정보를 제공합니다."
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

}
