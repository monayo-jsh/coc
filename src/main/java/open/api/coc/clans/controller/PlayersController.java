package open.api.coc.clans.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.player.persistence.repository.JpaPlayerRecordHistoryRepository;
import open.api.coc.clans.clean.infrastructure.season.repository.JpaSeasonEndManagementCustomRepository;
import open.api.coc.clans.service.PlayersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/players")
public class PlayersController {

    private final PlayersService playersService;

    @GetMapping("/legend/record")
    public ResponseEntity<List<String>> getLegendRecordPlayers() {
        return ResponseEntity.ok()
                             .body(playersService.findAllPlayersToRecord());
    }

}
