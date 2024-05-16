package open.api.coc.clans.controller;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.ClanAssignedMemberListResponse;
import open.api.coc.clans.domain.clans.ClanCapitalRaidSeasonResponse;
import open.api.coc.clans.domain.clans.ClanContent;
import open.api.coc.clans.domain.clans.ClanContentRequest;
import open.api.coc.clans.domain.clans.ClanCurrentWarRes;
import open.api.coc.clans.domain.clans.ClanMemberListRes;
import open.api.coc.clans.domain.clans.ClanRequest;
import open.api.coc.clans.domain.clans.ClanResponse;
import open.api.coc.clans.domain.clans.LeagueClanRes;
import open.api.coc.clans.schedule.ClanWarLeagueScheduler;
import open.api.coc.clans.service.ClansService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/clans")
public class ClansController {

    private final ClansService clansService;
    private final ClanWarLeagueScheduler scheduler;

    @GetMapping("")
    public ResponseEntity<List<ClanResponse>> getClans() {
        return ResponseEntity.ok()
                             .body(clansService.getClanList());
    }

    @PostMapping("{clanTag}")
    public ResponseEntity<ClanResponse> registerClan(@PathVariable String clanTag,
                                                     @RequestBody ClanRequest clanRequest) {
        ClanResponse clan = clansService.registerClan(clanRequest);
        return ResponseEntity.ok()
                             .body(clan);
    }

    @DeleteMapping("{clanTag}")
    public ResponseEntity<?> deleteClan(@PathVariable String clanTag) {
        clansService.deleteClan(clanTag);
        return ResponseEntity.ok().build();
    }

    @PutMapping("content")
    public ResponseEntity<ClanContentRequest> putContent(@RequestBody ClanContentRequest clanContentRequest) {

        ClanContent clanContent = ClanContent.create(clanContentRequest);
        clansService.updateClanContentStatus(clanContent);

        return ResponseEntity.ok()
                             .body(clanContentRequest);
    }

    @GetMapping("/war")
    public ResponseEntity<List<ClanResponse>> getClansWar(@RequestParam(defaultValue = "normal") String view) {

        List<ClanResponse> clanWarList = clansService.getClanWarResList();
        if ("parallel".equals(view)) {
            clanWarList = clansService.getClanWarParallelResList();
        }
        return ResponseEntity.ok()
                             .body(clanWarList);
    }
    @GetMapping("/league/{clanTag}")
    public ResponseEntity<LeagueClanRes> getClansLeagueWar(@PathVariable String clanTag) throws IOException {
        return ResponseEntity.ok(clansService.getLeagueClan(clanTag));
    }

    @GetMapping("/capital")
    public ResponseEntity<List<ClanResponse>> getClansCapital() {
        return ResponseEntity.ok()
                             .body(clansService.getClanCaptialList());
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

    @GetMapping("/{clanTag}/assigned/members")
    public ResponseEntity<ClanAssignedMemberListResponse> getClanAssignedMembers(@PathVariable String clanTag) {
        ClanAssignedMemberListResponse assignedMembers = clansService.findClanAssignedMembers(clanTag);
        return ResponseEntity.ok().body(assignedMembers);
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

    @GetMapping("/{clanTag}/current/war")
    public ResponseEntity<ClanCurrentWarRes> getClanCurrentWar(@PathVariable String clanTag) {
        ClanCurrentWarRes clanCurrentWar = clansService.getClanCurrentWar(clanTag);
        return ResponseEntity.ok().body(clanCurrentWar);
    }

    @GetMapping("/{clanTag}/capital/raid/seasons")
    public ResponseEntity<ClanCapitalRaidSeasonResponse> getClanCapitalRaidSeasons(@PathVariable String clanTag) {
        ClanCapitalRaidSeasonResponse clanCapitalRaidAttacker = clansService.getClanCapitalRaidSeason(clanTag);
        return ResponseEntity.ok().body(clanCapitalRaidAttacker);
    }


    @GetMapping("/league-war")
    public ResponseEntity<ClanCurrentWarRes> getClanWarLeagueRound(@RequestParam String clanTag, @RequestParam String roundTag) {
        return ResponseEntity.ok(clansService.getLeagueWar(clanTag, roundTag));
    }

    @GetMapping("/league-data-scheduling")
    public void leagueDataScheduling() throws IOException {
        scheduler.createWarRoundFile();
    }

}
