package open.api.coc.clans.clean.presentation.clan;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.clan.ClanWarUseCase;
import open.api.coc.clans.clean.application.clan.dto.ClanWarQuery;
import open.api.coc.clans.clean.application.clan.mapper.ClanWarUseCaseMapper;
import open.api.coc.clans.clean.presentation.clan.dto.war.ClanWarResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<ClanWarResponse>> getClanWarsFromServer(@RequestParam Long startDate,
                                                             @RequestParam Long endDate) {

        ClanWarQuery query = clanWarUseCaseMapper.toClanWarQuery(startDate, endDate);

        return ResponseEntity.ok()
                             .body(clanWarUseCase.getClanWarsFromServer(query));
    }


}
