package lt.code.academy.springhomeworkv5.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.IOException;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestTimerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug("______________________________________________________");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        filterChain.doFilter(servletRequest, servletResponse);

        stopWatch.stop();

        log.debug("{} request took {}", ((RequestFacade)servletRequest).getRequestURI(), stopWatch.getLastTaskTimeMillis());
        log.debug("______________________________________________________");
    }
}
