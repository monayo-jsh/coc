package open.api.coc.clans.controller.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class KakaoRedirectInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userAgent = request.getHeader("User-Agent");
        String requestUrl = request.getRequestURL().toString();

        if (userAgent != null && userAgent.contains("KAKAOTALK")) {
            String encoded = URLEncoder.encode(requestUrl, StandardCharsets.UTF_8);
            String redirectUrl = "kakaotalk://web/openExternal?url=" + encoded;
            response.sendRedirect(redirectUrl);
            return false; // 컨트롤러 진입 막음
        }

        return true; // 계속 진행
    }

}