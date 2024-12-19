package sit.int202.crud.filters;


import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long startAt = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);

        System.out.println("Completed request in " + (System.currentTimeMillis() - startAt) + " ms.");
    }
}
