package open.api.coc.clans.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.LabelResponse;
import open.api.coc.clans.service.LeaguesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name= "리그", description = "리그 관련")
@RestController
@RequiredArgsConstructor
@RequestMapping("/leagues")
public class LeaguesController {

    private final LeaguesService leaguesService;

    @Operation(
        summary = "리그 목록 조회 API, version: 1.00, Last Update: 24.08.14",
        description = "리그 목록 조회 API"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(schema = @Schema(implementation = LabelResponse.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<LabelResponse>> getLeagues() {
        List<LabelResponse> leagues = leaguesService.getLeagues();
        return ResponseEntity.ok().body(leagues);
    }

}
