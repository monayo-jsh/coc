package open.api.coc.clans.controller;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.service.ClanWarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final ClanWarService clanWarService;

    @GetMapping("/test")
    public void test() {
        clanWarService.saveClanWarResult();
    }

}
