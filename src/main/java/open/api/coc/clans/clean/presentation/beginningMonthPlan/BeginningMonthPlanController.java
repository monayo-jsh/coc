package open.api.coc.clans.clean.presentation.beginningMonthPlan;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.beginningMonthPlan.BeginningMonthPlanService;
import open.api.coc.clans.clean.domain.beginningMonthPlan.dto.BeginningMonthCreateCommand;
import open.api.coc.clans.clean.domain.beginningMonthPlan.mapper.BeginningMonthMapper;
import open.api.coc.clans.clean.presentation.beginningMonthPlan.dto.BeginningMonthCreateRequest;
import open.api.coc.clans.clean.presentation.beginningMonthPlan.dto.BeginningMonthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "월초 일정", description = "월초 일정 관련 기능")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/beginning-month-plan")
public class BeginningMonthPlanController {

    private final BeginningMonthPlanService beginningMonthPlanService;

    private final BeginningMonthMapper beginningMonthMapper;

    @Operation(
        summary = "월초 일정 목록을 조회합니다. version: 1.00, Last Update: 25.02.20",
        description = "이 API는 월초 일정 목록을 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BeginningMonthResponse.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<BeginningMonthResponse>> getBeginningMonths() {
        return ResponseEntity.ok()
                             .body(beginningMonthPlanService.gets());
    }


    @Operation(
        summary = "최근 월초 일정을 조회합니다. version: 1.00, Last Update: 25.02.20",
        description = "이 API는 최근 월초 일정을 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = BeginningMonthResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/latest")
    public ResponseEntity<BeginningMonthResponse> getBeginningMonthLatest() {
        return ResponseEntity.ok()
                             .body(beginningMonthPlanService.getLatest());
    }

    @Operation(
        summary = "월초 일정을 등록합니다. version: 1.00, Last Update: 25.01.02",
        description = "이 API는 월초 일정을 등록합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PostMapping("")
    public ResponseEntity<Void> postBeginningMonth(@Valid @RequestBody BeginningMonthCreateRequest request) {

        BeginningMonthCreateCommand command = beginningMonthMapper.toBeginningMonthCommand(request);
        beginningMonthPlanService.create(command);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
    }

    @Operation(
        summary = "월초 일정을 삭제합니다. version: 1.00, Last Update: 25.02.20",
        description = "이 API는 월초 일정을 삭제합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBeginningMonth(@PathVariable Long id) {
        beginningMonthPlanService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
    }

}
