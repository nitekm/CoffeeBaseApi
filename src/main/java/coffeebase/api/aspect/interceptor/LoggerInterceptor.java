package coffeebase.api.aspect.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;



public class LoggerInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        logger.info("[ENDPOINT CALLED: {} @{}]", request.getMethod(), request.getMethod());
        Long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex){
        if (response.getStatus() >= 200 && response.getStatus() <= 300 && ex == null) {
            logger.info("[SUCCESS: {} @{}, Http status: {}]", request.getMethod(), request.getRequestURI(), response.getStatus());
        } else {
            if (ex != null) {
                logger.error("[ERROR: {} @{}, Http status: {}, Error Message: {}]",
                        request.getMethod(), request.getRequestURI(), response.getStatus(), ex.getMessage());
            } else {
                logger.error("[ERROR: {} @{}, Http status: {}]",
                        request.getMethod(), request.getRequestURI(), response.getStatus());
            }
        }
        Long endTime = System.currentTimeMillis();
        Long startTime = (Long) request.getAttribute("startTime");
        Long executionTime = endTime - startTime;
        logger.info("[EXECUTION TIME: {} ms]", executionTime);
    }
}
