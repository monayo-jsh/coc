
package open.api.coc.clans.controller;

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
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.ClanAssignedMemberListResponse;
import open.api.coc.clans.domain.clans.ClanAssignedPlayerBulk;
import open.api.coc.clans.domain.clans.ClanAssignedPlayerBulkRequest;
import open.api.coc.clans.domain.clans.ClanContentCommand;
import open.api.coc.clans.domain.clans.ClanContentRequest;
import open.api.coc.clans.domain.clans.ClanCreateCommand;
import open.api.coc.clans.domain.clans.ClanCreateRequest;
import open.api.coc.clans.domain.clans.ClanCurrentWarLeagueGroupResponse;
import open.api.coc.clans.domain.clans.ClanCurrentWarResponse;
import open.api.coc.clans.domain.clans.ClanMemberListRes;
import open.api.coc.clans.domain.clans.ClanResponse;
import open.api.coc.clans.domain.clans.LeagueClanRes;
import open.api.coc.clans.schedule.ClanWarLeagueScheduler;
import open.api.coc.clans.service.ClansService;
import org.springframework.http.MediaType;
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

@Tag(name= "클랜", description = "클랜 기능 관련")
@RestController
@RequiredArgsConstructor
@RequestMapping("/clans")
public class ClansController {

    private final ClansService clansService;
    private final ClanWarLeagueScheduler scheduler;

    @Operation(
        summary = "클랜 목록 조회 API, version: 1.00, Last Update: 24.08.14",
        description = "클랜 목록 조회 API"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClanResponse.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<ClanResponse>> getClans() {
        return ResponseEntity.ok()
                             .body(clansService.getActiveClans());
    }

    @Operation(
        summary = "클랜 등록 API, version: 1.00, Last Update: 24.08.14",
        description = "클랜 등록 API",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "클랜 등록 객체",
            required = true,
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ClanCreateRequest.class)
            )
        )
    )
    @Parameters(value = {
        @Parameter(name = "clanTag", description = "클랜 태그", required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClanResponse.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PostMapping("/{clanTag}")
    public ResponseEntity<ClanResponse> registerClan(@PathVariable String clanTag,
                                                     @Valid @RequestBody ClanCreateRequest request) {

        ClanCreateCommand command = ClanCreateCommand.create(clanTag, request);
        ClanResponse clan = clansService.registerClan(command);

        return ResponseEntity.ok()
                             .body(clan);
    }

    @Operation(
        summary = "클랜 삭제 API, version: 1.00, Last Update: 24.08.22",
        description = "클랜 삭제 API"
    )
    @Parameters(value = {
        @Parameter(name = "clanTag", description = "클랜 태그", required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "성공 (No Content)"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @DeleteMapping("/{clanTag}")
    public ResponseEntity<Void> deleteClan(@PathVariable String clanTag) {
        clansService.deactivateClan(clanTag);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "클랜의 컨텐츠 정보를 수정합니다. version: 1.00, Last Update: 24.08.22",
        description = "이 API는 클랜의 컨텐츠 정보를 업데이트합니다. <br/>클랜 태그를 기반으로 해당 클랜의 다양한 컨텐츠 설정을 수정할 수 있습니다.",
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
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @PutMapping("/{clanTag}/content")
    public ResponseEntity<Void> putContent(@PathVariable String clanTag,
                                                         @RequestBody ClanContentRequest clanContentRequest) {

        ClanContentCommand command = ClanContentCommand.create(clanTag, clanContentRequest);
        clansService.updateClanContentStatus(command);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/war")
    public ResponseEntity<List<ClanResponse>> getWarClans(@RequestParam(defaultValue = "normal") String view) {

        List<ClanResponse> clanWarList = clansService.getWarClanResList();
        if ("parallel".equals(view)) {
            clanWarList = clansService.getWarParallelClanResList();
        }
        return ResponseEntity.ok()
                             .body(clanWarList);
    }

    @GetMapping("/war/league")
    public ResponseEntity<List<ClanResponse>> getWarLeagueClans() {

        List<ClanResponse> clanWarLeagueList = clansService.getWarLeagueClanResList();
        return ResponseEntity.ok()
                             .body(clanWarLeagueList);
    }

    @GetMapping("/league/{clanTag}")
    public ResponseEntity<LeagueClanRes> getClansLeagueWar(@PathVariable String clanTag) throws IOException {
        return ResponseEntity.ok(clansService.getLeagueClan(clanTag));
    }

    @GetMapping("/capital")
    public ResponseEntity<List<ClanResponse>> getClansCapital() {
        return ResponseEntity.ok()
                             .body(clansService.getActiveCapitalClans());
    }

    @GetMapping("/detail")
    public ResponseEntity<List<ClanResponse>> getClanDetail(@RequestParam List<String> clanTags) {
        List<ClanResponse> clans = clansService.findClanByClanTags(clanTags);
        return ResponseEntity.ok().body(clans);
    }

    @GetMapping("/members")
    public ResponseEntity<List<ClanMemberListRes>> getClanMembersByClanTags(@RequestParam List<String> clanTags) {
        List<ClanMemberListRes> clanMemberLists = clansService.findClanMembersByClanTags(clanTags);
        return ResponseEntity.ok().body(clanMemberLists);
    }

    @GetMapping("/{clanTag}")
    public ResponseEntity<ClanResponse> findClan(@PathVariable String clanTag) {
        ClanResponse clan = clansService.findClanByClanTag(clanTag);
        return ResponseEntity.ok().body(clan);
    }

    @GetMapping("/{clanTag}/members")
    public ResponseEntity<ClanMemberListRes> getClanMembers(@PathVariable String clanTag) {
        ClanMemberListRes clanMemberList = clansService.findClanMembersByClanTag(clanTag);
        return ResponseEntity.ok().body(clanMemberList);
    }

    @GetMapping("/assigned/members/latest")
    public ResponseEntity<ClanAssignedMemberListResponse> getLatestClanAssignedMembers() {
        ClanAssignedMemberListResponse clanAssignedMemberList = clansService.getLatestClanAssignedMembers();
        return ResponseEntity.ok().body(clanAssignedMemberList);
    }

    @GetMapping("/{clanTag}/assigned/members")
    public ResponseEntity<ClanAssignedMemberListResponse> getClanAssignedMembers(@PathVariable String clanTag) {
        ClanAssignedMemberListResponse assignedMembers = clansService.findClanAssignedMembers(clanTag);
        return ResponseEntity.ok().body(assignedMembers);
    }

    @PostMapping("/assigned/members")
    public ResponseEntity<?> postClanAssignedMembers(@RequestBody ClanAssignedPlayerBulkRequest request) {

        ClanAssignedPlayerBulk clanAssignedPlayerBulk = ClanAssignedPlayerBulk.create(request);

        clansService.registerClanAssignedMembers(clanAssignedPlayerBulk);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{clanTag}/assigned/{seasonDate}/{playerTag}")
    public ResponseEntity<?> postClanAssignedMember(@PathVariable String clanTag,
                                                    @PathVariable String seasonDate,
                                                    @PathVariable String playerTag) {

        clansService.postClanAssignedMember(clanTag, seasonDate, playerTag);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{clanTag}/assigned/{seasonDate}/{playerTag}")
    public ResponseEntity<?> deleteClanAssignedMember(@PathVariable String clanTag,
                                                      @PathVariable String seasonDate,
                                                      @PathVariable String playerTag) {

        clansService.deleteClanAssignedMember(clanTag, seasonDate, playerTag);

        return ResponseEntity.ok().build();
    }




    @GetMapping("/league/assigned/members/latest")
    public ResponseEntity<ClanAssignedMemberListResponse> getLatestLeagueAssignedMembers() {
        ClanAssignedMemberListResponse clanAssignedMemberList = clansService.getLatestLeagueAssignedMembers();
        return ResponseEntity.ok().body(clanAssignedMemberList);
    }

    @GetMapping("/{clanTag}/league/assigned/members")
    public ResponseEntity<ClanAssignedMemberListResponse> getClanLeagueAssignedMembers(@PathVariable String clanTag) {
        ClanAssignedMemberListResponse assignedMembers = clansService.findClanLeagueAssignedMembers(clanTag);
        return ResponseEntity.ok().body(assignedMembers);
    }

    @PostMapping("/{clanTag}/league/assigned/{seasonDate}/{playerTag}")
    public ResponseEntity<?> postLeagueAssignedMember(@PathVariable String clanTag,
                                                    @PathVariable String seasonDate,
                                                    @PathVariable String playerTag) {

        clansService.postLeagueAssignedMember(clanTag, seasonDate, playerTag);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{clanTag}/league/assigned/{seasonDate}/{playerTag}")
    public ResponseEntity<?> deleteClanLeagueAssignedMember(@PathVariable String clanTag,
                                                      @PathVariable String seasonDate,
                                                      @PathVariable String playerTag) {

        clansService.deleteClanLeagueAssignedMember(clanTag, seasonDate, playerTag);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/league/assigned/members")
    public ResponseEntity<?> postClanLeagueAssignedMembers(@RequestBody ClanAssignedPlayerBulkRequest request) {

        ClanAssignedPlayerBulk clanAssignedPlayerBulk = ClanAssignedPlayerBulk.create(request);

        clansService.registerClanLeagueAssignedMembers(clanAssignedPlayerBulk);

        return ResponseEntity.ok().build();
    }




    @GetMapping("/{clanTag}/current/war")
    public ResponseEntity<ClanCurrentWarResponse> getClanCurrentWar(@PathVariable String clanTag) {
        ClanCurrentWarResponse clanCurrentWar = clansService.getClanCurrentWar(clanTag);
        return ResponseEntity.ok().body(clanCurrentWar);
    }


    @GetMapping("/league-war")
    public ResponseEntity<ClanCurrentWarResponse> getClanWarLeagueRound(@RequestParam String clanTag, @RequestParam String roundTag) {
        return ResponseEntity.ok(clansService.getLeagueWar(clanTag, roundTag));
    }

    @GetMapping("/league-data-scheduling")
    public void leagueDataScheduling() throws IOException {
        scheduler.createWarRoundFile();
    }











    @GetMapping("/{clanTag}/current/war/league")
    public ResponseEntity<ClanCurrentWarLeagueGroupResponse> getClanCurrentWarLeagueGroup(@PathVariable String clanTag) {
        ClanCurrentWarLeagueGroupResponse clanCurrentWarLeagueGroupResponse = clansService.getClanCurrentWarLeagueGroup(clanTag);
        return ResponseEntity.ok().body(clanCurrentWarLeagueGroupResponse);
    }

    @GetMapping("/war/league/{warTag}")
    public ResponseEntity<ClanCurrentWarResponse> getClanWarLeague(@PathVariable String warTag,
                                                                   @RequestParam("clanTag") String clanTag,
                                                                   @RequestParam("season") String season) {
        ClanCurrentWarResponse ClanCurrentWarRes = clansService.getClanWarLeagueRound(clanTag, season, warTag);
        return ResponseEntity.ok().body(ClanCurrentWarRes);
    }

}
