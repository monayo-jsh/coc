package open.api.coc.clans.clean.presentation.player;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.presentation.player.dto.PlayerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "플레이어", description = "플레이어 관련 기능")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/players")
public class PlayerController {


    @Operation(
        summary = "플레이어 목록을 조회합니다. version: 1.00, Last Update: 24.09.30",
        description = "이 API는 서버에 플레이어 목록을 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(arraySchema = @Schema(implementation = PlayerResponse.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<PlayerResponse>> getPlayers() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(Collections.emptyList());
    }

}