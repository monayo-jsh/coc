package open.api.coc.clans.controller;

import lombok.RequiredArgsConstructor;
import open.api.coc.external.coc.config.ClashOfClanConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("clans")
public class ClansController {

    @GetMapping("{clanTag}")
    public ResponseEntity<?> findClanByClanTag(@PathVariable String clanTag) {
        return ResponseEntity.ok(clanTag);
    }
}
