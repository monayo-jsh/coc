package open.api.coc.clans.clean.presentation.season;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.season.repository.SeasonRepository;
import open.api.coc.clans.clean.domain.season.service.SeasonDeletionService;
import open.api.coc.clans.clean.domain.season.service.SeasonRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "시즌", description = "시즌 기능 관련")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/season")
public class SeasonController {

    private final SeasonRepository seasonRepository;
    private final SeasonRegisterService seasonRegisterService;
    private final SeasonDeletionService seasonDeletionService;


    @Operation(
        summary = "설정된 시즌 종료일 목록을 조회합니다. version: 1.00, Last Update: 24.11.27",
        description = "이 API는 설정된 시즌 종료일 최근 목록 10개를 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(schema = @Schema(implementation = LocalDate.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping(value = "/latest")
    public ResponseEntity<List<LocalDate>> getLatestSeasons() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(seasonRepository.findLatestSeasonEndDate(10));
    }

    @Operation(
        summary = "신규 시즌 종료일 설정합니다. version: 1.00, Last Update: 24.11.27",
        description = "이 API는 신규 시즌 종료일 설정합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PostMapping(value = "{endDate}")
    public ResponseEntity<Void> postEndDate(@PathVariable Long endDate) {
        seasonRegisterService.create(endDate);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .build();
    }

    @Operation(
        summary = "시즌 종료일 제거합니다. version: 1.00, Last Update: 24.11.27",
        description = "이 API는 시즌 종료일 제거합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @DeleteMapping(value = "{endDate}")
    public ResponseEntity<Void> deleteEndDate(@PathVariable Long endDate) {
        seasonDeletionService.delete(endDate);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
    }
}
