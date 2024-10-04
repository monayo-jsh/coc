package open.api.coc.clans.clean.presentation.event;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.event.EventUseCase;
import open.api.coc.clans.clean.presentation.event.dto.EventTeamLegendResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "이벤트", description = "이벤트 관련 기능")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event")
public class EventController {

    private final EventUseCase eventUseCase;

    @Operation(
        summary = "최근 팀 전설내기 이벤트를 조회합니다. version: 1.00, Last Update: 24.10.4",
        description = "이 API는 현재 진행중인 팀 전설내기 이벤트 정보를 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = EventTeamLegendResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/team/legend/latest")
    public ResponseEntity<EventTeamLegendResponse> getTeamLegendLatest() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(eventUseCase.getLatestTeamLegend());
    }

}
