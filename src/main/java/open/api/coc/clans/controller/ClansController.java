
package open.api.coc.clans.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.ClanAssignedMemberListResponse;
import open.api.coc.clans.domain.clans.ClanAssignedPlayerBulk;
import open.api.coc.clans.domain.clans.ClanAssignedPlayerBulkRequest;
import open.api.coc.clans.domain.clans.ClanCurrentWarLeagueGroupResponse;
import open.api.coc.clans.domain.clans.ClanCurrentWarResponse;
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
