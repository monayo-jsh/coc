package open.api.coc.clans.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanWarRecordDTO;
import open.api.coc.clans.domain.clans.ClanWarMemberQuery;
import open.api.coc.clans.domain.clans.ClanWarMemberResponse;
import open.api.coc.clans.domain.clans.ClanWarMissingAttackPlayerDTO;
import open.api.coc.clans.domain.clans.ClanWarResponse;
import open.api.coc.clans.domain.clans.converter.TimeUtils;
import open.api.coc.clans.service.ClanWarService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clan/war")
public class ClanWarController {

    private final TimeUtils timeUtils;
    private final ClanWarService clanWarService;

    @GetMapping("/period")
    public ResponseEntity<List<ClanWarResponse>> getClanWars(@RequestParam LocalDate startDate,
                                                             @RequestParam LocalDate endDate) {
        return ResponseEntity.ok()
                             .body(clanWarService.getClanWars(startDate, endDate));
    }

    @GetMapping("/missing/attack/period")
    public ResponseEntity<List<ClanWarMissingAttackPlayerDTO>> getClanWarMissingAttackPlayers(@RequestParam LocalDate startDate,
                                                                                              @RequestParam LocalDate endDate) {
        return ResponseEntity.ok()
                             .body(clanWarService.getClanWarMissingAttackPlayers(startDate, endDate));
    }

    @GetMapping("/missing/attack/{playerTag}")
    public ResponseEntity<List<ClanWarMissingAttackPlayerDTO>> getClanWarMissingAttackPlayerWithTagForRecentDays(@PathVariable String playerTag,
                                                                                                                 @RequestParam(value = "queryDate", defaultValue = "90") Integer queryDate) {

        return ResponseEntity.ok(clanWarService.getClanWarMissingAttackPlayersWithTag(playerTag, queryDate));
    }

    @GetMapping("/missing/attack/playerName")
    public ResponseEntity<List<ClanWarMissingAttackPlayerDTO>> getClanWarMissingAttackPlayerWithNameForRecentDays(@RequestParam String playerName,
                                                                                                                  @RequestParam(value = "queryDate", defaultValue = "90") Integer queryDate) {

        return ResponseEntity.ok(clanWarService.getClanWarMissingAttackPlayersWithName(playerName, queryDate));

    }


    @GetMapping("{warId}")
    public ResponseEntity<ClanWarResponse> getClanWarDetail(@PathVariable Long warId) {
        return ResponseEntity.ok()
                             .body(clanWarService.getClanWarDetail(warId));
    }

    @GetMapping("/members")
    public ResponseEntity<List<ClanWarMemberResponse>> getClanWarMembers(@RequestParam String clanTag,
                                                                         @RequestParam Long startTime,
                                                                         @RequestParam(required = false) String necessaryAttackYn) {

        ClanWarMemberQuery clanWarMemberQuery = ClanWarMemberQuery.create(clanTag, timeUtils.toLocalDateTime(startTime), necessaryAttackYn);

        return ResponseEntity.ok()
                             .body(clanWarService.getClanWarMembers(clanWarMemberQuery));
    }

    @GetMapping("/ranking/stars")
    public ResponseEntity<List<ClanWarRecordDTO>> getRankingClanWarStars(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate searchMonth,
                                                                         @RequestParam String clanTag,
                                                                         @RequestParam(defaultValue = "") String searchType) {
        return ResponseEntity.ok()
                             .body(clanWarService.getRankingClanWarStars(searchMonth, clanTag, searchType));
    }

    @GetMapping("/league/ranking/stars")
    public ResponseEntity<List<ClanWarRecordDTO>> getRankingLeagueClanWarStars(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate searchMonth,
                                                                               @RequestParam String clanTag,
                                                                               @RequestParam(defaultValue = "") String searchType) {
        return ResponseEntity.ok()
                             .body(clanWarService.getRankingLeagueClanWarStars(searchMonth, clanTag, searchType));
    }

    @PutMapping("/{warId}/{playerTag}/necessary")
    public ResponseEntity<ClanWarMemberResponse> updateClanWarMemberAttackNecessaryAttack(@PathVariable Long warId,
                                                                                          @PathVariable String playerTag) {
        return ResponseEntity.ok()
                             .body(clanWarService.updateClanWarMemberAttackNecessaryAttack(warId, playerTag));
    }
}
