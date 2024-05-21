package open.api.coc.clans.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clan/cms")
public class ViewAdminController {

    /** 운영 기능 페이지 */

    @GetMapping("")
    public String viewAdminHome() {
        return "cms/Admin";
    }

    @GetMapping("/member/manager")
    public String viewAdminMemberManager() {
        return "cms/MemberManager";
    }

    @GetMapping("/clan/manager")
    public String viewAdminClanManager() {
        return "cms/ClanManager";
    }

    @GetMapping("/login")
    public String viewAdminLoginForm() {
        return "cms/Login";
    }

}
