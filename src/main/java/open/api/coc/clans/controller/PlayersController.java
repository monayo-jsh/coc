package open.api.coc.clans.controller;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.season.repository.JpaSeasonEndManagementCustomRepository;
import open.api.coc.clans.database.repository.player.PlayerRecordHistoryRepository;
import open.api.coc.clans.domain.players.PlayerModify;
import open.api.coc.clans.domain.players.PlayerModifyRequest;
import open.api.coc.clans.domain.players.PlayerRecordResponse;
import open.api.coc.clans.domain.players.PlayerResponse;
import open.api.coc.clans.domain.players.RankingHeroEquipmentResponse;
import open.api.coc.clans.domain.players.SupportPlayerBulkRequest;
import open.api.coc.clans.domain.ranking.RankingHallOfFame;
import open.api.coc.clans.domain.ranking.RankingHallOfFameDTO;
import open.api.coc.clans.domain.ranking.RankingHallOfFameDonationDTO;
import open.api.coc.clans.service.PlayersService;
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
@RequestMapping("/players")
public class PlayersController {

    private final PlayersService playersService;

    // 추 후 리팩토링하면서 제거 예정
    private final JpaSeasonEndManagementCustomRepository jpaSeasonEndManagementCustomRepository;
    private final PlayerRecordHistoryRepository playerRecordHistoryRepository;

    @GetMapping("/all")
    public ResponseEntity<List<PlayerResponse>> getAllPlayer() {
        return ResponseEntity.ok()
                             .body(playersService.findAllPlayers());
    }

    @GetMapping("/all/summary")
    public ResponseEntity<List<PlayerResponse>> getAllPlayerSummary() {
        return ResponseEntity.ok()
                             .body(playersService.findAllPlayersSummary());
    }

    @GetMapping("/summary")
    public ResponseEntity<List<PlayerResponse>> getAllPlayerSummary(String name) {
        return ResponseEntity.ok()
                             .body(playersService.findPlayersSummary(name));
    }

    @GetMapping("/all/tags")
    public ResponseEntity<List<String>> getAllPlayerTags() {
        return ResponseEntity.ok()
                             .body(playersService.findAllPlayerTags());
    }

    @GetMapping("")
    public ResponseEntity<List<PlayerResponse>> getPlayer(@RequestParam List<String> playerTags) {
        return ResponseEntity.ok()
                             .body(playersService.findPlayerBy(playerTags));
    }

    @GetMapping("/{playerTag}")
    public ResponseEntity<PlayerResponse> getPlayer(@PathVariable String playerTag) {
        return ResponseEntity.ok()
                             .body(playersService.findPlayerBy(playerTag));
    }

    @PostMapping("/{playerTag}")
    public ResponseEntity<PlayerResponse> savePlayer(@PathVariable String playerTag) {

        PlayerResponse player = playersService.registerPlayer(playerTag);

        return ResponseEntity.ok().body(player);
    }

    @PutMapping("/{playerTag}")
    public ResponseEntity<?> modifyPlayer(@PathVariable String playerTag) {

        playersService.updatePlayer("api", playerTag);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{playerTag}")
    public ResponseEntity<?> deletePlayer(@PathVariable String playerTag) {

        playersService.deletePlayer(playerTag);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/support/all")
    public ResponseEntity<List<PlayerResponse>> getAllSupportPlayer() {
        return ResponseEntity.ok()
                             .body(playersService.findAllSupportPlayers());
    }

    @PostMapping("/support/bulk")
    public ResponseEntity<?> registerSupportPlayerBulk(@Valid @RequestBody SupportPlayerBulkRequest request) {

        playersService.registerSupportPlayerBulk(request.getPlayerTags());

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{playerTag}/support")
    public ResponseEntity<?> updatePlayerSupport(@PathVariable String playerTag,
                                                 @Valid @RequestBody PlayerModifyRequest request) {

        PlayerModify playerModify = PlayerModify.create(playerTag, request);
        playersService.changePlayerSupport(playerModify);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/ranking/hero/equipments")
    public ResponseEntity<List<RankingHeroEquipmentResponse>> getRankingHeroEquipments(@RequestParam String clanTag) {
        return ResponseEntity.ok()
                             .body(playersService.getRankingHeroEquipments(clanTag));
    }

    @GetMapping("/ranking/trophies/current")
    public ResponseEntity<List<RankingHallOfFame>> getRankingTrophiesCurrent() {
        return ResponseEntity.ok()
                             .body(playersService.getRankingTrophiesCurrent());
    }

    @GetMapping("/ranking/attack/wins")
    public ResponseEntity<List<RankingHallOfFame>> getRankingAttackWins() {
        return ResponseEntity.ok()
                             .body(playersService.getRankingAttackWins());
    }

    @GetMapping("/ranking/donations")
    public ResponseEntity<List<RankingHallOfFameDonationDTO>> getRankingDonations() {
        return ResponseEntity.ok()
                             .body(playersService.getRankingDonations());
    }

    @GetMapping("/ranking/donations/received")
    public ResponseEntity<List<RankingHallOfFameDTO>> getRankingDonationsReceived() {
        return ResponseEntity.ok()
                             .body(playersService.getRankingDonationsReceived());
    }

    @GetMapping("/legend/record")
    public ResponseEntity<List<String>> getLegendRecordPlayers() {
        return ResponseEntity.ok()
                             .body(playersService.findAllPlayersToRecord());
    }

    @GetMapping("/{playerTag}/legend/record")
    public ResponseEntity<List<PlayerRecordResponse>> getPlayerLegendRecord(@PathVariable String playerTag) {
        List<LocalDate> seasonEndDates = jpaSeasonEndManagementCustomRepository.findLatestSeasonEndDate(2);
        LocalTime seasonTime = LocalTime.of(14, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(seasonEndDates.get(0), seasonTime);
        LocalDateTime startDateTime = LocalDateTime.of(seasonEndDates.get(1), seasonTime);
        return ResponseEntity.ok()
                             .body(playerRecordHistoryRepository.findAllById(playerTag, startDateTime, endDateTime));
    }
}
