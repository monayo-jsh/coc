package open.api.coc.clans.clean.presentation.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KakaoRedirectController {

    @GetMapping("/kakaotalk-redirect")
    public String showRedirectPage(@RequestParam String target, Model model) {
        model.addAttribute("targetUrl", target);
        return "kakaotalk-redirect";
    }

}
