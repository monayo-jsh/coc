package open.api.coc.clans.clean.presentation.player;

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
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.player.PlayerUseCase;
import open.api.coc.clans.clean.application.player.mapper.PlayerUseCaseMapper;
import open.api.coc.clans.clean.application.player.model.PlayerListSearchQuery;
import open.api.coc.clans.clean.application.player.model.PlayerSupportUpdateBulkCommand;
import open.api.coc.clans.clean.application.player.model.PlayerSupportUpdateCommand;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerLegendRecordTargetDTO;
import open.api.coc.clans.clean.presentation.common.dto.RankingHallOfFameResponse;
import open.api.coc.clans.clean.presentation.player.dto.PlayerLegendRecordResponse;
import open.api.coc.clans.clean.presentation.player.dto.PlayerResponse;
import open.api.coc.clans.clean.presentation.player.dto.PlayerSupportUpdateBulkRequest;
import open.api.coc.clans.clean.presentation.player.dto.PlayerSupportUpdateRequest;
import open.api.coc.clans.clean.presentation.player.dto.RankingHallOfFameDonationResponse;
import open.api.coc.clans.clean.presentation.player.dto.RankingHeroEquipmentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "플레이어", description = "플레이어 관련 기능")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerUseCase playerUseCase;

    private final PlayerUseCaseMapper playerUseCaseMapper;

    @Operation(
        summary = "플레이어 전체 목록을 조회합니다. version: 1.00, Last Update: 24.09.30",
        description = "이 API는 서버에 등록된 플레이어 전체 목록으로 제공됩니다."
    )
    @Parameters(
        value = {
            @Parameter(name = "viewMode", description = "조회 모드 (detail: 전체 제공, summary: 플레이어 영웅, 영웅장비까지만 제공)", required = false),
            @Parameter(name = "accountType", description = "계정 유형 (support: 지원계정)", required = false),
            @Parameter(name = "name", description = "이름 검색", required = false)
        }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(arraySchema = @Schema(implementation = PlayerResponse.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<PlayerResponse>> getAllPlayers(@RequestParam(defaultValue = "all") String accountType,
                                                              @RequestParam(defaultValue = "detail") String viewMode,
                                                              @RequestParam(required = false) String name) {

        PlayerListSearchQuery query = playerUseCaseMapper.toPlayerListSearchQuery(accountType, viewMode, name);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(playerUseCase.getAllPlayers(query));
    }

    @Operation(
        summary = "플레이어 전체 태그 목록을 조회합니다. version: 1.00, Last Update: 24.09.30",
        description = "이 API는 서버에 등록된 플레이어 전체 태그 목록으로 제공됩니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(arraySchema = @Schema(implementation = String.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/tags")
    public ResponseEntity<List<String>> getAllPlayerTags() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(playerUseCase.getAllPlayerTags());
    }

    @Operation(
        summary = "플레이어 정보를 조회(Open API)합니다. version: 1.00, Last Update: 24.10.28",
        description = "이 API는 Open API 연동한 플레이어 정보를 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = PlayerResponse.class))),
        @ApiResponse(responseCode = "404", description = "플레이어 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/{playerTag}/external")
    public ResponseEntity<PlayerResponse> getPlayerFromExternal(@PathVariable String playerTag) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(playerUseCase.getPlayerFromExternal(playerTag));
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
        summary = "플레이어의 닉네임을 설정합니다. version: 1.00, Last Update: 24.10.31",
        description = "이 API는 서버에 저장된 플레이어의 닉네임을 설정합니다."
    )
    @Parameters(
        value = {
            @Parameter(name = "nickname", description = "설정할 닉네임", required = true)
        }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = Void.class))),
        @ApiResponse(responseCode = "404", description = "플레이어 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PutMapping("/{playerTag}/nickname")
    public ResponseEntity<Void> putPlayerNickname(@PathVariable String playerTag,
                                                  @RequestParam String nickname) {
        playerUseCase.settingPlayerNickname(playerTag, nickname);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
    }

    @Operation(
        summary = "플레이어를 삭제합니다. version: 1.00, Last Update: 24.10.17",
        description = "이 API는 서버 등록된 플레이어를 삭제합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = Void.class))),
        @ApiResponse(responseCode = "404", description = "플레이어 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @DeleteMapping("/{playerTag}")
    public ResponseEntity<Void> removePlayer(@PathVariable String playerTag) {
        playerUseCase.removePlayer(playerTag);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
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

    @Operation(
        summary = "플레이어 지원계정 등록/해제 기능을 제공합니다. version: 1.00, Last Update: 24.10.21",
        description = "이 API는 플레이어를 지원계정으로 등록 또는 해제합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = Void.class))),
        @ApiResponse(responseCode = "404", description = "플레이어 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PutMapping("/{playerTag}/support")
    public ResponseEntity<Void> putPlayerSupport(@PathVariable String playerTag,
                                                 @Valid @RequestBody PlayerSupportUpdateRequest request) {
        PlayerSupportUpdateCommand command = playerUseCaseMapper.toSupportUpdateCommand(playerTag, request);
        playerUseCase.changePlayerSupportType(command);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
    }

    @Operation(
        summary = "플레이어 지원계정 일괄 재설정 기능을 제공합니다. version: 1.00, Last Update: 24.10.21",
        description = "이 API는 플레이어 지원계정 설정을 초기화 후 일괄 등록합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = Void.class))),
        @ApiResponse(responseCode = "404", description = "플레이어 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PutMapping("/support/bulk")
    public ResponseEntity<Void> putPlayerSupportBulk(@Valid @RequestBody PlayerSupportUpdateBulkRequest request) {
        PlayerSupportUpdateBulkCommand command = playerUseCaseMapper.toSupportUpdateBulkCommand(request);
        playerUseCase.changePlayerSupportTypeBulk(command);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
    }

    @Operation(
        summary = "수집된 플레이어 전설기록을 제공합니다. version: 1.00, Last Update: 24.10.30",
        description = "이 API는 수집된 플레이어의 전설기록을 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(arraySchema = @Schema(implementation = PlayerLegendRecordResponse.class)))),
        @ApiResponse(responseCode = "404", description = "플레이어 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/{playerTag}/legend/record")
    public ResponseEntity<List<PlayerLegendRecordResponse>> getLegendRecord(@PathVariable String playerTag) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(playerUseCase.getLegendRecord(playerTag));
    }

    @Operation(
        summary = "현재 플레이어 트로피 순위 목록을 제공합니다. version: 1.00, Last Update: 24.10.21",
        description = "이 API는 서버에 등록된 플레이어의 현재 트로피 순위 목록을 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(arraySchema = @Schema(implementation = RankingHallOfFameResponse.class)))),
        @ApiResponse(responseCode = "404", description = "플레이어 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/ranking/trophies")
    public ResponseEntity<List<RankingHallOfFameResponse>> getRankingTrophies() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(playerUseCase.getRankingTrophies());
    }

    @Operation(
        summary = "현재 플레이어 공성 순위 목록을 제공합니다. version: 1.00, Last Update: 24.10.28",
        description = "이 API는 서버에 등록된 플레이어의 현재 공성 순위 목록을 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(arraySchema = @Schema(implementation = RankingHallOfFameResponse.class)))),
        @ApiResponse(responseCode = "404", description = "플레이어 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/ranking/attack/wins")
    public ResponseEntity<List<RankingHallOfFameResponse>> getRankingAttackWins() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(playerUseCase.getRankingAttackWins());
    }

    @Operation(
        summary = "현재 플레이어 지원 순위 목록을 제공합니다. version: 1.00, Last Update: 24.10.28",
        description = "이 API는 서버에 등록된 플레이어의 현재 지원 순위 목록을 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(arraySchema = @Schema(implementation = RankingHallOfFameDonationResponse.class)))),
        @ApiResponse(responseCode = "404", description = "플레이어 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/ranking/donations")
    public ResponseEntity<List<RankingHallOfFameDonationResponse>> getRankingDonations() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(playerUseCase.getRankingDonations());
    }

    @Operation(
        summary = "현재 플레이어 지원 받은 순위 목록을 제공합니다. version: 1.00, Last Update: 24.10.28",
        description = "이 API는 서버에 등록된 플레이어의 현재 지원 받은 순위 목록을 제공합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(arraySchema = @Schema(implementation = RankingHallOfFameResponse.class)))),
        @ApiResponse(responseCode = "404", description = "플레이어 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/ranking/donations/received")
    public ResponseEntity<List<RankingHallOfFameResponse>> getRankingDonationReceived() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(playerUseCase.getRankingDonationReceived());
    }

    @Operation(
        summary = "플레이어 영웅 장비 착용 순위 목록을 제공합니다. version: 1.00, Last Update: 24.10.29",
        description = "이 API는 플레이어 영웅 장비 착용 순위 목록을 제공합니다."
    )
    @Parameters(value = {
        @Parameter(name = "clanTag", description = "클랜 태그")
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(arraySchema = @Schema(implementation = RankingHeroEquipmentResponse.class)))),
        @ApiResponse(responseCode = "404", description = "플레이어 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/ranking/hero/equipments")
    public ResponseEntity<List<RankingHeroEquipmentResponse>> getRankingHeroEquipments(@RequestParam String clanTag) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(playerUseCase.getRankingHeroEquipments(clanTag));
    }

    @Operation(
        summary = "전설 기록 수집 등록된 플레이어 태그 목록을 제공합니다. version: 1.00, Last Update: 24.10.31",
        description = "이 API는 전설 기록 수집 등록된 플레이어 태그 목록을 제공합니다."
    )
    @Parameters(
        value = {
            @Parameter(name = "name", description = "이름 검색", required = true)
        }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(arraySchema = @Schema(implementation = PlayerLegendRecordTargetDTO.class)))),
        @ApiResponse(responseCode = "404", description = "플레이어 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/legend/record/target")
    public ResponseEntity<List<PlayerLegendRecordTargetDTO>> getPlayerLegendRecordTarget(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(playerUseCase.findAllLegendRecordTags(name));
    }

    @Operation(
        summary = "전설 기록 수집 대상 플레이어로 등록합니다. version: 1.00, Last Update: 24.10.31",
        description = "이 API는 전설 기록 수집 대상 플레이어로 등록합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(schema = @Schema(implementation = Void.class))),
        @ApiResponse(responseCode = "404", description = "플레이어 정보 없음", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PostMapping("/{playerTag}/legend/record")
    public ResponseEntity<Void> postPlayerLegendRecord(@PathVariable String playerTag) {
        playerUseCase.registerPlayerLegendRecord(playerTag);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
    }

}
