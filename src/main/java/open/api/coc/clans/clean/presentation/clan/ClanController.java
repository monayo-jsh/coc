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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.clan.ClanUseCase;
import open.api.coc.clans.clean.application.clan.dto.ClanContentUpdateCommand;
import open.api.coc.clans.clean.application.clan.dto.ClanQueryCommand;
import open.api.coc.clans.clean.application.clan.mapper.ClanUseCaseMapper;
import open.api.coc.clans.clean.presentation.clan.dto.ClanContentRequest;
import open.api.coc.clans.clean.presentation.clan.dto.ClanResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "클랜", description = "클랜 기능 관련")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/clans")
public class ClanController {

    private final ClanUseCase clanUseCase;
    private final ClanUseCaseMapper clanUseCaseMapper;

    @Operation(
        summary = "클랜 목록을 조회합니다. version: 1.00, Last Update: 24.11.20",
        description = "이 API는 클랜 목록을 제공합니다."
    )
    @Parameters(value = {
        @Parameter(name = "type", description = "조회 유형 (none: 클랜전, parallel: 병행클랜전, league: 리그전, capital: 습격전, competition: 대회)", required = false)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClanResponse.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<ClanResponse>> getClans(@RequestParam(required = false) String type) {
        ClanQueryCommand command = clanUseCaseMapper.toClanQueryCommand(type);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(clanUseCase.getActiveClans(command));
    }

    @Operation(
        summary = "클랜 정보를 등록합니다., version: 1.00, Last Update: 24.11.20",
        description = "이 API는 클랜 정보를 등록합니다."
    )
    @Parameters(value = {
        @Parameter(name = "clanTag", description = "클랜 태그", required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공 응답 Body", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClanResponse.class)))),
        @ApiResponse(responseCode = "404", description = "클랜 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PostMapping("/{clanTag}")
    public ResponseEntity<ClanResponse> registerClan(@PathVariable @NotBlank String clanTag) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(clanUseCase.registerClan(clanTag));
    }

    @Operation(
        summary = "클랜 정보를 삭제합니다., version: 1.00, Last Update: 24.11.20",
        description = "이 API는 클랜 정보를 삭제합니다."
    )
    @Parameters(value = {
        @Parameter(name = "clanTag", description = "클랜 태그", required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "성공 (No Content)"),
        @ApiResponse(responseCode = "404", description = "클랜 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @DeleteMapping("/{clanTag}")
    public ResponseEntity<Void> deleteClan(@PathVariable @NotBlank String clanTag) {
        clanUseCase.deleteClan(clanTag);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
    }

    @Operation(
        summary = "클랜의 컨텐츠 활성화 정보를 수정합니다. version: 1.00, Last Update: 24.11.20",
        description = "이 API는 클랜의 컨텐츠 활성화 정보를 업데이트합니다. <br/>클랜 태그를 기반으로 해당 클랜의 컨텐츠 활성화 여부를 수정할 수 있습니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "클랜 컨텐츠 수정 객체",
            required = true,
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ClanContentRequest.class)
            )
        )
    )
    @Parameters(value = {
        @Parameter(name = "clanTag", description = "클랜 태그", required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "성공 (No Content)"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = Object.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PutMapping("/{clanTag}/content")
    public ResponseEntity<Void> putContent(@PathVariable @NotBlank String clanTag,
                                           @Valid @RequestBody ClanContentRequest request) {

        ClanContentUpdateCommand command = clanUseCaseMapper.toClanContentUpdateCommand(clanTag, request);
        clanUseCase.updateContentActivation(command);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
    }

}