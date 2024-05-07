package open.api.coc.clans.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("")
    public String viewHome() {
        return "Home";
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
    public String viewAdmin() {
        return "clan/League";
    }

    @GetMapping("/capital/raid")
    public String viewCapitalRaid() {
        return "capital/Raid";
    }

}
