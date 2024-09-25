package open.api.coc.clans.clean.presentation.raid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.raid.RaidUseCase;
import open.api.coc.clans.clean.presentation.raid.dto.ClanCapitalRaidResponse;
import open.api.coc.clans.clean.presentation.raid.dto.ClanCapitalRaidScoreResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "클랜 캐피탈", description = "클랜 캐피탈 관련 기능")
@RestController(value = "raidV2Controller")
@RequiredArgsConstructor
@RequestMapping("/api/raid/v2")
public class RaidController {

    private final RaidUseCase raidUseCase;

    @Operation(
        summary = "클랜의 현재 시즌 캐피탈 정보를 조회(실시간 연동)합니다. version: 1.00, Last Update: 24.09.25",
        description = "이 API는 클랜의 현재 시즌 캐피탈 정보를 실시간 연동하여 제공합니다.<br>연동 결과는 서버에 저장합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = ClanCapitalRaidResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/{clanTag}/seasons/current")
    public ResponseEntity<ClanCapitalRaidResponse> getClanCapitalRaidCurrentSeason(@PathVariable String clanTag) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(raidUseCase.getClanCapitalCurrentSeason(clanTag));
    }

    @Operation(
        summary = "캐피탈 활성화 클랜들의 현재 시즌 캐피탈 데이터 수집을 수행합니다., version: 1.00, Last Update: 24.09.26",
        description = "이 API는 캐피탈 활성화 클랜들의 현재 시즌 캐피탈 데이터를 연동 후 서버에 수집합니다,"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = Void.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PostMapping("/seasons/current/collect")
    public ResponseEntity<Void> collectClanCapitalRaidSeason() {
        raidUseCase.collectClanCapitalCurrentSeason();
        return ResponseEntity.ok()
                             .build();
    }

    @Operation(
        summary = "현재 시즌 캐피탈 공격 기록 정보를 조회합니다. version: 1.00, Last Update: 24.09.25",
        description = "이 API는 서버에 수집된 현재 시즌 캐피탈 공격 기록 정보를 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = ClanCapitalRaidScoreResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/seasons/current/attacks")
    public ResponseEntity<List<ClanCapitalRaidScoreResponse>> getCurrentSeasonCapitalAttacks() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(raidUseCase.getCurrentSeasonCapitalAttacks());
    }

}
