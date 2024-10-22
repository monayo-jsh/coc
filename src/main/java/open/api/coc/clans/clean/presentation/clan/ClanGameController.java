package open.api.coc.clans.clean.presentation.clan;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanGameDTO;
import open.api.coc.clans.clean.domain.clan.service.ClanGameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "클랜 게임 관련")
@RestController
@RequiredArgsConstructor
@RequestMapping("/clan/game")
public class ClanGameController {

    private final ClanGameService clanGameService;

    @Operation(
        summary = "마지막 클랜 게임 목록을 조회한다. version: 1.00, Last Update: 24.10.22",
        description = "이 API는 마지막으로 진행된 클랜 게임 목록을 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = ClanGameDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/latest")
    public ResponseEntity<List<ClanGameDTO>> getLatestClanGames() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(clanGameService.getLatestClanGames());
    }

}
