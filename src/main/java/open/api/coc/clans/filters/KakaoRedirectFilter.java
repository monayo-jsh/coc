package open.api.coc.clans.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Component;

@Component
public class KakaoRedirectFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String userAgent = req.getHeader("User-Agent");

        if (isRequestAPI(req.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        if (isNotKakaoTalkAgent(userAgent, req)) {
            // 이미 외부 브라우저이거나, API 호출은 그대로 통과
            chain.doFilter(request, response);
            return;
        }


        // 카카오 인앱에서 접근한 경우 → 안내 페이지로 리디렉션
        String redirectTarget = URLEncoder.encode(req.getRequestURL().toString(), StandardCharsets.UTF_8);
        res.sendRedirect("/kakaotalk-redirect?target=" + redirectTarget);
    }

    private boolean isRequestAPI(String uri) {
        // 카카오톡 안내 페이지
        if (uri.startsWith("/kakaotalk-redirect")) { return true; }
        // API
        if (uri.startsWith("/api/")) { return true; }
        // 리소스
        if (uri.startsWith("/favicon.ico")) { return true; }
        if (uri.startsWith("/js/")) { return true; }
        if (uri.startsWith("/css/")) { return true; }
        if (uri.startsWith("/lib/")) { return true; }
        return false;
    }

    private boolean isNotKakaoTalkAgent(String userAgent, HttpServletRequest req) {
        if (userAgent == null || userAgent.isEmpty()) { return true; }
        return !userAgent.contains("KAKAOTALK");
    }

}
