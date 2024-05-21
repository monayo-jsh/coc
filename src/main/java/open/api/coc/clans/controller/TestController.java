package open.api.coc.clans.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.schedule.RaidScheduler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final RaidScheduler raidScheduler;
    @GetMapping("/ttt")
    public ResponseEntity<String> test() {
        raidScheduler.raidScheduling();
        return ResponseEntity.ok("123");
    }
}
