package open.api.coc.clans.clean.presentation.clan;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.clan.ClanWarUseCase;
import open.api.coc.clans.clean.application.clan.dto.war.ClanWarMemberLeagueRecordQuery;
import open.api.coc.clans.clean.application.clan.dto.war.ClanWarMemberQuery;
import open.api.coc.clans.clean.application.clan.dto.war.ClanWarMissingAttackPlayerQuery;
import open.api.coc.clans.clean.application.clan.dto.war.ClanWarMissingAttackQuery;
import open.api.coc.clans.clean.application.clan.dto.war.ClanWarQuery;
import open.api.coc.clans.clean.application.clan.dto.war.ClanWarMemberRecordQuery;
import open.api.coc.clans.clean.application.clan.mapper.ClanWarUseCaseMapper;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarDetailResponse;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarMemberMissingAttackResponse;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarMemberRecordResponse;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarParticipantResponse;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "클랜 전쟁 기능", description = "클랜 전쟁 기능 관련")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clan/war")
public class ClanWarController {

    private final ClanWarUseCase clanWarUseCase;
    private final ClanWarUseCaseMapper clanWarUseCaseMapper;

    @Operation(
        summary = "서버에 수집된 클랜 전쟁 목록을 조회한다. version: 1.00, Last Update: 24.11.14",
        description = "이 API는 서버에 수집된 클랜 전쟁 목록을 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = ClanWarResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/period")
    public ResponseEntity<List<ClanWarResponse>> getClanWarsFromServer(@RequestParam Long startDate, @RequestParam Long endDate) {
        ClanWarQuery query = clanWarUseCaseMapper.toClanWarQuery(startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(clanWarUseCase.getClanWarsFromServer(query));
    }

    @Operation(
        summary = "서버에 수집된 클랜 전쟁 상세 정보를 조회한다. version: 1.00, Last Update: 24.11.15",
        description = "이 API는 서버에 수집된 클랜 전쟁 상세 정보를 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = ClanWarDetailResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("{warId}")
    public ResponseEntity<ClanWarDetailResponse> getClanWarDetail(@PathVariable Long warId) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(clanWarUseCase.getClanWarDetail(warId));
    }

    @Operation(
        summary = "서버에 수집된 클랜 전쟁 참여자 목록을 조회한다. version: 1.00, Last Update: 24.11.18",
        description = "이 API는 서버에 수집된 클랜 전쟁 참여자 목록을 제공합니다."
    )
    @Parameters(
        value = {
            @Parameter(name = "clanTag", description = "클랜 태그"),
            @Parameter(name = "startTime", description = "전쟁 시작일시"),
            @Parameter(name = "necessaryAttackYn", description = "필수 참여 여부: YN", required = false),
        }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = ClanWarParticipantResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/participants")
    public ResponseEntity<List<ClanWarParticipantResponse>> getClanWarParticipants(@RequestParam @NotBlank(message = "클랜 태그를 입력해주세요.") String clanTag,
                                                                                   @RequestParam Long startTime,
                                                                                   @RequestParam(required = false) @Pattern(regexp = "[YN]") String necessaryAttackYn) {
        ClanWarMemberQuery query = clanWarUseCaseMapper.toClanWarMemberQuery(clanTag, startTime, necessaryAttackYn);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(clanWarUseCase.getClanWarParticipants(query));
    }

    @Operation(
        summary = "클랜 전쟁 참여자의 필수 참여 여부를 수정한다. version: 1.00, Last Update: 24.11.18",
        description = "이 API는 클랜 전쟁 참여자의 필수 참여 여부를 수정합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = Void.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PutMapping("/{warId}/{playerTag}/necessary")
    public ResponseEntity<Void> putClanWarMemberAttackNecessaryAttack(@PathVariable Long warId, @PathVariable String playerTag) {
        clanWarUseCase.changeClanWarMemberAttackNecessaryAttack(warId, playerTag);
        return ResponseEntity.status(HttpStatus.OK)
                             .build();
    }

    @Operation(
        summary = "서버에 수집된 클랜 전쟁 미공 참여자 목록을 조회한다. version: 1.00, Last Update: 24.11.18",
        description = "이 API는 서버에 수집된 클랜 전쟁 미공 참여자 목록을 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = ClanWarMemberMissingAttackResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/missing/attack/period")
    public ResponseEntity<List<ClanWarMemberMissingAttackResponse>> getClanWarMissingAttackPlayers(@RequestParam Long startDate, @RequestParam Long endDate) {
        ClanWarMissingAttackQuery query = clanWarUseCaseMapper.toClanWarMissingAttackQuery(startDate, endDate);
        return ResponseEntity.ok()
                             .body(clanWarUseCase.getClanWarMissingAttackPlayers(query));
    }

    @Operation(
        summary = "서버에 수집된 플레이어의 클랜 전쟁 미공 기록을 조회한다. version: 1.00, Last Update: 24.11.19",
        description = "이 API는 서버에 수집된 플레이어의 클랜 전쟁 미공 기록을 제공합니다."
    )
    @Parameters(
        value = {
            @Parameter(name = "tag", description = "플레이어 태그", required = false),
            @Parameter(name = "name", description = "플레이어 이름", required = false),
            @Parameter(name = "queryDate", description = "조회 기간, default: 90일", required = false),
        }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = ClanWarMemberMissingAttackResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/missing/attack")
    public ResponseEntity<List<ClanWarMemberMissingAttackResponse>> getClanWarMissingAttacks(@RequestParam(required = false) @Pattern(regexp = "^#.*", message = "#으로 시작해야합니다.") String tag,
                                                                                             @RequestParam(required = false) String name,
                                                                                             @RequestParam(defaultValue = "90") Integer queryDate) {
        ClanWarMissingAttackPlayerQuery query = clanWarUseCaseMapper.toClanWarMissingAttackPlayerQuery(tag, name, queryDate);
        return ResponseEntity.ok()
                             .body(clanWarUseCase.getClanWarMissingAttacks(query));
    }

    @Operation(
        summary = "서버에 수집된 클랜전 획득별 랭킹을 조회한다. version: 1.00, Last Update: 24.11.19",
        description = "이 API는 서버에 수집된 클랜전 획득별 랭킹을 제공합니다."
    )
    @Parameters(
        value = {
            @Parameter(name = "type", description = "조회 유형, all: 전체 순위 제공", required = false),
            @Parameter(name = "month", description = "조회 기준 월"),
            @Parameter(name = "clanTag", description = "클랜 태그")
        }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = ClanWarMemberRecordResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/record")
    public ResponseEntity<List<ClanWarMemberRecordResponse>> getClanWarRecords(@RequestParam(required = false) String type,
                                                                               @RequestParam Long month,
                                                                               @RequestParam String clanTag) {
        ClanWarMemberRecordQuery query = clanWarUseCaseMapper.toClanWarRecordQuery(type, month, clanTag);
        return ResponseEntity.ok()
                             .body(clanWarUseCase.getClanWarMemberRecords(query));
    }

    @Operation(
        summary = "서버에 수집된 리그전 획득별 랭킹을 조회한다. version: 1.00, Last Update: 24.11.19",
        description = "이 API는 서버에 수집된 리그전 획득별 랭킹을 제공합니다."
    )
    @Parameters(
        value = {
            @Parameter(name = "type", description = "조회 유형, all: 전체 순위 제공", required = false),
            @Parameter(name = "month", description = "조회 기준 월"),
            @Parameter(name = "clanTag", description = "클랜 태그"),
            @Parameter(name = "isPerfect", description = "완파 여부, default: false", required = false)
        }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = ClanWarMemberRecordResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/league/record")
    public ResponseEntity<List<ClanWarMemberRecordResponse>> getLeagueWarRecords(@RequestParam(required = false) String type,
                                                                                 @RequestParam Long month,
                                                                                 @RequestParam String clanTag,
                                                                                 @RequestParam(required = false, defaultValue = "false") Boolean isPerfect) {
        ClanWarMemberLeagueRecordQuery query = clanWarUseCaseMapper.toLeagueWarRecordQuery(type, month, clanTag, isPerfect);
        return ResponseEntity.ok()
                             .body(clanWarUseCase.getLeagueWarMemberRecords(query));
    }
}
