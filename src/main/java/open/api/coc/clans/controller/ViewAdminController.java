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

    /** 운영 기능 페이지 */
    @GetMapping("/clan/manager")
    public String viewAdminClanManager() {
        return "cms/ClanManager";
    }
}
