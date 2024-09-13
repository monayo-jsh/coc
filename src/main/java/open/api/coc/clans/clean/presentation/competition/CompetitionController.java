package open.api.coc.clans.clean.presentation.competition;

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
import open.api.coc.clans.clean.application.competition.CompetitionUseCase;
import open.api.coc.clans.clean.application.competition.mapper.CompetitionUseCaseMapper;
import open.api.coc.clans.clean.application.competition.model.CompetitionCreateCommand;
import open.api.coc.clans.clean.application.competition.model.CompetitionParticipateCreateCommand;
import open.api.coc.clans.clean.application.competition.model.CompetitionUpdateCommand;
import open.api.coc.clans.clean.presentation.competition.dto.CompetitionCreateRequest;
import open.api.coc.clans.clean.presentation.competition.dto.CompetitionResponse;
import open.api.coc.clans.clean.presentation.competition.dto.CompetitionUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/competitions")
@Tag(name = "대회", description = "대회 관리")
public class CompetitionController {

    private final CompetitionUseCaseMapper competitionUseCaseMapper;
    private final CompetitionUseCase competitionUseCase;

    @Operation(
        summary = "등록된 대회 목록을 조회합니다. version: 1.00, Last Update: 24.09.13",
        description = "이 API는 등록된 대회 목록을 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CompetitionResponse.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<CompetitionResponse>> getCompetitions() {
        List<CompetitionResponse> competitions = competitionUseCase.getCompetitions();
        return ResponseEntity.status(HttpStatus.OK)
                             .body(competitions);
    }

    @Operation(
        summary = "등록된 대회 정보를 조회합니다. version: 1.00, Last Update: 24.09.13",
        description = "이 API는 등록된 대회 정보를 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = CompetitionResponse.class))),
        @ApiResponse(responseCode = "404", description = "대회 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("{competitionId}")
    public ResponseEntity<CompetitionResponse> getCompetition(@PathVariable Long competitionId) {
        CompetitionResponse competition = competitionUseCase.getCompetition(competitionId);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(competition);
    }

    @Operation(
        summary = "대회를 등록합니다. version: 1.00, Last Update: 24.09.13",
        description = "이 API는 대회를 등록합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = CompetitionResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PostMapping("")
    public ResponseEntity<CompetitionResponse> postCompetition(@Valid @RequestBody CompetitionCreateRequest request) {

        CompetitionCreateCommand command = competitionUseCaseMapper.toCreateCommand(request);
        CompetitionResponse competition = competitionUseCase.create(command);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(competition);
    }

    @Operation(
        summary = "대회를 수정합니다. version: 1.00, Last Update: 24.09.13",
        description = "이 API는 대회 정보를 수정합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = Void.class))),
        @ApiResponse(responseCode = "404", description = "대회 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PutMapping("/{competitionId}")
    public ResponseEntity<Void> putCompetition(@PathVariable Long competitionId,
                                                              @Valid @RequestBody CompetitionUpdateRequest request) {

        CompetitionUpdateCommand command = competitionUseCaseMapper.toUpdateCommand(competitionId, request);
        competitionUseCase.update(command);

        return ResponseEntity.status(HttpStatus.OK)
                             .build();
    }

    @Operation(
        summary = "대회에 참가 신청합니다. version: 1.00, Last Update: 24.09.13",
        description = "이 API는 대회에 참가 신청합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공 응답 - 대회 참가 고유 키", content = @Content(schema = @Schema(implementation = Long.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "대회 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PostMapping("/{competitionId}/participate/{clanTag}")
    public ResponseEntity<Long> postCompetitionParticipate(@PathVariable Long competitionId,
                                                           @PathVariable String clanTag) {

        CompetitionParticipateCreateCommand command = competitionUseCaseMapper.toParticipateCreateCommand(competitionId, clanTag);
        Long participateId = competitionUseCase.participate(command);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(participateId);
    }

}