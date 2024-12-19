package sit.int202.crud.interceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Random;

public class LogInterception implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startAt = System.currentTimeMillis();
        System.out.println("Request started at " + startAt);
        System.out.println("Request url: " + request.getRequestURI());

        double random = new Random().nextDouble();

        if(random < 0.5) {
            System.out.println("Request rejected");
            response.setStatus(503);
            return false;
        }
        System.out.println("Request accepted");
        request.setAttribute("startAt", startAt);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startAt = (long) request.getAttribute("startAt");
        System.out.println("Completed request in " + (System.currentTimeMillis() - startAt) + " ms.");
    }
}
