
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
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.ClanAssignedMemberListResponse;
import open.api.coc.clans.domain.clans.ClanAssignedPlayerBulk;
import open.api.coc.clans.domain.clans.ClanAssignedPlayerBulkRequest;
import open.api.coc.clans.domain.clans.ClanCurrentWarLeagueGroupResponse;
import open.api.coc.clans.domain.clans.ClanCurrentWarResponse;
import open.api.coc.clans.domain.clans.ClanMemberListRes;
import open.api.coc.clans.domain.clans.ClanResponse;
import open.api.coc.clans.schedule.ClanWarLeagueScheduler;
import open.api.coc.clans.service.ClansService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        summary = "캐피탈 활성화된 클랜 목록을 조회합니다. version: 1.00, Last Update: 24.08.22",
        description = "이 API는 캐피탈 활성화된 클랜 목록을 반환합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClanResponse.class)))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/capital")
    public ResponseEntity<List<ClanResponse>> getClansCapital() {
        return ResponseEntity.ok()
                             .body(clansService.getActiveCapitalClans());
    }

    @Operation(
        summary = "클랜 상세 정보 목록을 조회합니다. (실시간 연동) version: 1.00, Last Update: 24.08.22",
        description = "이 API는 클랜 상세 정보 목록을 실시간 연동 결과로 반환합니다."
            + "<br>- 리그전 정보가 변경된 경우 서버에 현행화합니다."
    )
    @Parameters(value = {
        @Parameter(name = "clanTags", description = "클랜 태그 목록")
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClanResponse.class)))),
        @ApiResponse(responseCode = "400", description = "잘못된 파라미터 요청", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/detail")
    public ResponseEntity<List<ClanResponse>> getClanDetail(@RequestParam List<String> clanTags) {
        return ResponseEntity.ok()
                             .body(clansService.getClanDetailByClanTags(clanTags));
    }

    @Operation(
        summary = "클랜의 가입중인 플레이어 목록을 조회합니다. (실시간 연동) version: 1.00, Last Update: 24.08.22",
        description = "이 API는 클랜의 가입중인 플레이어 목록을 실시간 연동 결과로 반환합니다."
    )
    @Parameters(value = {
        @Parameter(name = "clanTags", description = "클랜 태그 목록")
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공 응답 Body", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClanMemberListRes.class)))),
        @ApiResponse(responseCode = "400", description = "잘못된 파라미터 요청", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/members")
    public ResponseEntity<List<ClanMemberListRes>> getClanMembersByClanTags(@RequestParam List<String> clanTags) {
        return ResponseEntity.ok()
                             .body(clansService.getClanMembersByClanTags(clanTags));
    }

    @GetMapping("/{clanTag}")
    public ResponseEntity<ClanResponse> findClan(@PathVariable String clanTag) {
        ClanResponse clan = clansService.findClanByClanTag(clanTag);
        return ResponseEntity.ok().body(clan);
    }

    @GetMapping("/assigned/latest")
    public ResponseEntity<String> getLatestClanAssignedDate() {
        String clanAssignedDate = clansService.getLatestClanAssignedDate();
        return ResponseEntity.ok().body(clanAssignedDate);
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



    @GetMapping("/league/assigned/latest")
    public ResponseEntity<String> getLatestLeagueAssignedDate() {
        String leagueAssignedDate = clansService.getLatestLeagueAssignedDate();
        return ResponseEntity.ok().body(leagueAssignedDate);
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
