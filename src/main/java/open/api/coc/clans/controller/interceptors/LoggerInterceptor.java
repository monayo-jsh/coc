package open.api.coc.clans.controller.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (isRequestView(handler)) {
            log.info("\n[REQUEST-VIEW]: {}\n[REMOTE-IP]: {}", request.getRequestURI(), request.getRemoteAddr());
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean isRequestView(Object handler) {
        String controllerClassName = ((HandlerMethod) handler).getBean().getClass().getSimpleName();
        return controllerClassName.startsWith("View");
    }
}
