package open.api.coc.clans.clean.presentation.clan;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.repository.ClanAssignRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "클랜 배정", description = "클랜 배정 기능 관련")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/clan/assign")
public class ClanAssignController {

    private final ClanAssignRepository clanAssignRepository;

    @Operation(
        summary = "최근 클랜 배정월을 조회합니다. version: 1.00, Last Update: 24.11.22",
        description = "이 API는 최근 클랜 배정월을 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/latest-month")
    public ResponseEntity<String> getLatestClanAssignedMonth() {
        return ResponseEntity.ok()
                             .body(clanAssignRepository.findLatestAssignedMonth());
    }

}
