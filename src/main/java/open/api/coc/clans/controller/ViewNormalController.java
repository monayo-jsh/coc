package open.api.coc.clans.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ViewNormalController {

    /** 편의 기능 페이지 */
    @GetMapping("")
    public String viewHome() {
        return "Home";
    }

    @GetMapping("/clan")
    public String viewClan() {
        return "clan/Clan";
    }

    @GetMapping("/lab/links")
    public String viewLabLinks() {
        return "lab/Links";
    }

    @GetMapping("/clan/member")
    public String viewClanMember() {
        return "clan/Member";
    }

    @GetMapping("/clan/war")
    public String viewClanWar() {
        return "clan/War";
    }

    @GetMapping("/clan/league")
    public String viewClanLeague() {
        return "clan/League";
    }

    @GetMapping("/clan/league/new")
    public String viewClanLeagueNew() {
        return "clan/LeagueNew";
    }

    @GetMapping("/capital/raid")
    public String viewCapitalRaid() {
        return "capital/Raid";
    }

}
