package open.api.coc.clans.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.LabelResponse;
import open.api.coc.clans.service.LeaguesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leagues")
public class LeaguesController {

    private final LeaguesService leaguesService;

    @GetMapping("")
    public ResponseEntity<List<LabelResponse>> getLeagues() {
        List<LabelResponse> leagues = leaguesService.getLeagues();
        return ResponseEntity.ok().body(leagues);
    }

}
