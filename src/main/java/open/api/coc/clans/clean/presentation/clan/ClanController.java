package open.api.coc.clans.clean.presentation.clan;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.clan.ClanUseCase;
import open.api.coc.clans.clean.presentation.clan.dto.ClanResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "클랜", description = "클랜 기능 관련")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/clans")
public class ClanController {

    private final ClanUseCase clanUseCase;

    @Operation(
        summary = "클랜 목록을 조회합니다. version: 1.00, Last Update: 24.11.20",
        description = "이 API는 클랜 목록을 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClanResponse.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<ClanResponse>> getClans() {
        return ResponseEntity.ok()
                             .body(clanUseCase.getClans());
    }

    @Operation(
        summary = "클랜 정보를 등록합니다., version: 1.00, Last Update: 24.08.14",
        description = "이 API는 클랜 정보를 등록합니다."
    )
    @Parameters(value = {
        @Parameter(name = "clanTag", description = "클랜 태그", required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClanResponse.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PostMapping("/{clanTag}")
    public ResponseEntity<ClanResponse> registerClan(@PathVariable String clanTag) {

        return ResponseEntity.ok()
                             .body(clanUseCase.registerClan(clanTag));
    }

}