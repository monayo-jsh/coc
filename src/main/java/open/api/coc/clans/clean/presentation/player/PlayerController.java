package open.api.coc.clans.clean.presentation.player;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.player.PlayerUseCase;
import open.api.coc.clans.clean.presentation.player.dto.PlayerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "플레이어", description = "플레이어 관련 기능")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerUseCase playerUseCase;

    @Operation(
        summary = "플레이어 목록을 조회합니다. version: 1.00, Last Update: 24.09.30",
        description = "이 API는 서버에 등록된 플레이어 목록으로 제공됩니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(arraySchema = @Schema(implementation = PlayerResponse.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<PlayerResponse>> getPlayers() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(playerUseCase.getAllPlayers());
    }

    @Operation(
        summary = "플레이어 목록(요약)을 조회합니다. version: 1.00, Last Update: 24.10.17",
        description = "이 API는 서버에 등록된 플레이어 목록으로 제공됩니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(arraySchema = @Schema(implementation = PlayerResponse.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/summary")
    public ResponseEntity<List<PlayerResponse>> getSummarizedPlayers() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(playerUseCase.getAllSummarizedPlayers());
    }

    @Operation(
        summary = "플레이어 정보를 조회합니다. version: 1.00, Last Update: 24.10.02",
        description = "이 API는 서버에 등록된 플레이어 정보로 제공됩니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = PlayerResponse.class))),
        @ApiResponse(responseCode = "404", description = "플레이어 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/{playerTag}")
    public ResponseEntity<PlayerResponse> getPlayer(@PathVariable String playerTag) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(playerUseCase.getPlayer(playerTag));
    }

    @Operation(
        summary = "플레이어를 저장합니다. version: 1.00, Last Update: 24.10.02",
        description = "이 API는 서버에 플레이어를 저장합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = PlayerResponse.class))),
        @ApiResponse(responseCode = "404", description = "플레이어 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PostMapping("/{playerTag}")
    public ResponseEntity<PlayerResponse> postPlayer(@PathVariable String playerTag) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(playerUseCase.registerPlayer(playerTag));
    }

    @Operation(
        summary = "플레이어를 현행화합니다. version: 1.00, Last Update: 24.10.16",
        description = "이 API는 서버에 저장된 플레이어 정보를 현행화합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = Void.class))),
        @ApiResponse(responseCode = "404", description = "플레이어 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PostMapping("/{playerTag}/synchronize")
    public ResponseEntity<Void> syncPlayer(@PathVariable String playerTag) {
        playerUseCase.synchronizePlayer(playerTag);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
    }
}
