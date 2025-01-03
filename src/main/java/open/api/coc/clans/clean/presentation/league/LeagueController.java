package open.api.coc.clans.clean.presentation.league;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.league.service.LeagueService;
import open.api.coc.clans.clean.presentation.league.dto.LeagueResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name= "리그", description = "리그 기능 관련")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/leagues")
public class LeagueController {

    private final LeagueService leagueService;

    @Operation(
        summary = "리그 목록을 조회합니다. version: 1.00, Last Update: 24.08.14",
        description = "이 API는 서버에 저장된 리그 목록을 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(schema = @Schema(implementation = LeagueResponse.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<LeagueResponse>> getLeagues() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(leagueService.getLeagues());
    }

}
