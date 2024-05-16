package open.api.coc.clans.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.players.PlayerResponse;
import open.api.coc.clans.service.PlayersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/players")
public class PlayersController {

    private final PlayersService playersService;

    @GetMapping("/all")
    public ResponseEntity<List<PlayerResponse>> getAllPlayer() {
        return ResponseEntity.ok()
                             .body(playersService.findAllPlayers());
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
    public ResponseEntity<?> savePlayer(@PathVariable String playerTag) {

        playersService.registerPlayer(playerTag);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{playerTag}")
    public ResponseEntity<?> modifyPlayer(@PathVariable String playerTag) {

        playersService.updatePlayer(playerTag);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{playerTag}")
    public ResponseEntity<?> deletePlayer(@PathVariable String playerTag) {

        playersService.deletePlayer(playerTag);

        return ResponseEntity.ok().build();
    }
}
