package open.api.coc.clans.clean.presentation.clan;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.clan.ClanWarUseCase;
import open.api.coc.clans.clean.application.clan.dto.ClanWarMemberQuery;
import open.api.coc.clans.clean.application.clan.dto.ClanWarQuery;
import open.api.coc.clans.clean.application.clan.mapper.ClanWarUseCaseMapper;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarDetailResponse;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarMemberGetRequest;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarMemberResponse;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "클랜 전쟁 기능", description = "클랜 전쟁 기능 관련")
@RestController(value = "ClanWarController2")
@RequiredArgsConstructor
@RequestMapping("/v2/api/clan/war")
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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = ClanWarMemberResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/members")
    public ResponseEntity<List<ClanWarMemberResponse>> getClanWarMembers(@Valid ClanWarMemberGetRequest request) {
        ClanWarMemberQuery query = clanWarUseCaseMapper.toClanWarMemberQuery(request);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(clanWarUseCase.getClanWarMembers(query));
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
}
