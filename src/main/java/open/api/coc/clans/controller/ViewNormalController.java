package open.api.coc.clans.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ViewNormalController {

    /** 편의 기능 홈 */
    @GetMapping("")
    public String home() {
        return "Home";
    }

    /** 클랜원 목록 */
    @GetMapping("/clan/member")
    public String clanMember() {
        return "clan/Member";
    }

    /** 클랜 목록 */
    @GetMapping("/clan")
    public String clan() {
        return "clan/Clan";
    }

    /** 연구소 */
    @GetMapping("/lab/links")
    public String labLinks() {
        return "lab/Links";
    }

    /** 영웅 장비 랭킹 */
    @GetMapping("/hero/equipments")
    public String rankingHeroEquipments() {
        return "ranking/HeroEquipments";
    }

    /** 습격전 현황 */
    @GetMapping("/capital/raid")
    public String capitalRaid() {
        return "capital/Raid";
    }

    /** 클랜전 현황 */
    @GetMapping("/clan/war")
    public String clanWar() {
        return "clan/War";
    }

    /** 리그전 현황 */
    @GetMapping("/clan/league")
    public String clanLeague() {
        return "clan/League";
    }

    /** 클랜전 현황 (new) */
    @GetMapping("/clan/league/new")
    public String clanLeagueNew() {
        return "clan/LeagueNew";
    }

}
