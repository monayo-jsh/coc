package open.api.coc.clans.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.service.ClansService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("clans")
public class ClansController {

    private final ClansService clansService;

    @GetMapping("{clanTag}")
    public ResponseEntity<?> findClan(@PathVariable String clanTag) {
        Map<String, Object> clan = clansService.findClanByClanTag(clanTag);
        return ResponseEntity.ok().body(clan);
    }

}
