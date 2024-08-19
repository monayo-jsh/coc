package open.api.coc.clans.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clan/cms")
public class ViewAdminController {

    /** 운영 기능 로그인 페이지 */
    @GetMapping("/login")
    public String loginForm() {
        return "cms/Login";
    }

    /** 홈 */
    @GetMapping("")
    public String adminHome() {
        return "cms/Admin";
    }

    /** 클랜원 관리 */
    @GetMapping("/member/manager")
    public String memberManager() {
        return "cms/MemberManager";
    }

    /** 지원계정 관리 */
    @GetMapping("/member/support/manager")
    public String memberSupportManager() {
        return "cms/MemberSupportManager";
    }

    /** 클랜 관리 */
    @GetMapping("/clan/manager")
    public String clanManager() {
        return "cms/ClanManager";
    }

    /** 클랜전 캘린더 */
    @GetMapping("/clan/war/calendar")
    public String clanWarCalendar() {
        return "cms/ClanWarCalendar";
    }

    /** 클랜전 월 완파 현황 */
    @GetMapping("/clan/war/statistics/player")
    public String clanWarStatistics() {
        return "cms/ClanWarStatisticsPlayer";
    }

    /** 클랜전 미공 캘린더 */
    @GetMapping("/clan/war/calendar/missing")
    public String clanWarMissingCalendar() {
        return "cms/ClanWarMissingCalendar";
    }

    /** 클랜전 미공 인원 검색 */
    @GetMapping("/clan/war/member/missing")
    public String clanWarMissingMember() {
        return "cms/ClanWarMissingMember";
    }

    /** 습격전 위반 현황 */
    @GetMapping("/raid/attack/season")
    public String raidMissingAttack() {
        return "cms/RaidAttackSeason";
    }

    /** 습격전 위반 현황 */
    @GetMapping("/raid")
    public String raid() {
        return "cms/Raid";
    }

    /** 습격전 기록 조회 */
    @GetMapping("/raid/score")
    public String raidScore() {
        return "cms/RaidScore";
    }


}