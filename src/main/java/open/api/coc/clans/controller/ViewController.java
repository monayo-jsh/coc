package open.api.coc.clans.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String viewHome() {
        return "Home";
    }

    @GetMapping("/capital/attacks")
    public String viewCapitalAttacks() {
        return "capital/Attacks";
    }

    @GetMapping("/clan/war")
    public String viewClanWar() {
        return "clan/War";
    }

}