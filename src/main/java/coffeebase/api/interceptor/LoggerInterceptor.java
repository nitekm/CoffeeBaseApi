package coffeebase.api.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggerInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        logger.info("[preHandle] " + request.getMethod() + ":" + request.getRequestURI());
        return true;
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex){
        logger.info("[Completed] " + request.getMethod() + ":" + request.getRequestURI());
    }
}
