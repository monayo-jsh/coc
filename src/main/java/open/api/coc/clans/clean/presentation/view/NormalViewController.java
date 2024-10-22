package open.api.coc.clans.clean.presentation.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class NormalViewController {

    /** 편의 기능 홈 */
    @GetMapping("")
    public String home() {
        return "Home";
    }

    /** 명예의 전당 */
    @GetMapping("/hall-of-fame")
    public String hallOfFame() {
        return "HallOfFame";
    }

    /** 팀 전설내기 이벤트 */
    @GetMapping("/event/team/legend")
    public String event() {
        return "event/TeamLegend";
    }

    /** 전설 기록 확인용 */
    @GetMapping("/test/legend/record")
    public String playerLegendRecord() {
        return "player/legendRecord";
    }

    /** 클랜원 목록 */
    @GetMapping("/clan/member")
    public String clanMember() {
        return "clan/Member";
    }

    /** 클랜원 목록 */
    @GetMapping("/find/member")
    public String findClanMember() {
        return "clan/FindMember";
    }

    /** 연구소 */
    @GetMapping("/lab/links")
    public String labLinks() {
        return "lab/Links";
    }

    /** 클랜 목록 */
    @GetMapping("/clan")
    public String clan() {
        return "clan/Clan";
    }

    /** 클랜 배정표 */
    @GetMapping("/assigned-member")
    public String clanAssigned() {
        return "clan/AssignedMember";
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

    /** 클랜게임 현황 */
    @GetMapping("/clan-game")
    public String clanGame() {
        return "clan/Game";
    }
}
