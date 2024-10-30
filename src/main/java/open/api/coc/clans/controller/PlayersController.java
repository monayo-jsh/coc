package open.api.coc.clans.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.player.persistence.repository.JpaPlayerRecordHistoryRepository;
import open.api.coc.clans.clean.infrastructure.season.repository.JpaSeasonEndManagementCustomRepository;
import open.api.coc.clans.domain.players.PlayerRecordResponse;
import open.api.coc.clans.service.PlayersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/players")
public class PlayersController {

    private final PlayersService playersService;

    // 추 후 리팩토링하면서 제거 예정
    private final JpaSeasonEndManagementCustomRepository jpaSeasonEndManagementCustomRepository;
    private final JpaPlayerRecordHistoryRepository playerRecordHistoryRepository;

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
