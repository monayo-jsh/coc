package open.api.coc.clans.controller;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.ClanCapitalRaidSeasonResponse;
import open.api.coc.clans.domain.clans.ClanCurrentWarRes;
import open.api.coc.clans.domain.clans.ClanMemberListRes;
import open.api.coc.clans.domain.clans.ClanResponse;
import open.api.coc.clans.schedule.ClanWarLeagueScheduler;
import open.api.coc.clans.service.ClansService;
import open.api.coc.external.coc.clan.ClanApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clans")
public class ClansController {

    private final ClansService clansService;
    private final ClanApiService clanApiService;
    private final ClanWarLeagueScheduler scheduler;

    @GetMapping("")
    public ResponseEntity<List<ClanResponse>> getClans() {
        return ResponseEntity.ok()
                             .body(clansService.getClanResList());
    }

    @GetMapping("/war")
    public ResponseEntity<List<ClanResponse>> getClansWar() {
        return ResponseEntity.ok()
                             .body(clansService.getClanWarResList());
    }

    @GetMapping("/capital")
    public ResponseEntity<List<ClanResponse>> getClansCapital() {
        return ResponseEntity.ok()
                             .body(clansService.getClanCaptialList());
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

    @GetMapping("/test/{clanTag}")
    public ResponseEntity<String> test(@PathVariable String clanTag) throws IOException {
        scheduler.createWarRoundFile();
        return ResponseEntity.ok(clanApiService.findClanWarLeagueRoundTags(clanTag).toString());
    }

}
