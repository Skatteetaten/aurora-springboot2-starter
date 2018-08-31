package no.skatteetaten.aurora.logging;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@ConditionalOnProperty(prefix = "aurora.starter.requestbasedlogging", name = "enabled", matchIfMissing = true)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MdcRequestFilter implements Filter {

    private static final String LOG_LEVEL = "X-REQUEST-LOG-LEVEL";
    private final Integer requestWaitTimeSeconds;
    private long lastRun = 0;

    public MdcRequestFilter(@Value("${aurora.starter.requestbasedlogging.wait:30}") Integer requestWaitTimeSeconds) {
        this.requestWaitTimeSeconds = requestWaitTimeSeconds;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Not used
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        String logLevel = ((HttpServletRequest) request).getHeader(LOG_LEVEL);
        if (logLevel != null
            && ("trace".equalsIgnoreCase(logLevel.trim()) || "debug".equalsIgnoreCase(logLevel.trim()))
            && accept(System.nanoTime(), lastRun, requestWaitTimeSeconds)) {
            try {
                logLevel = logLevel.trim().toUpperCase();
                MDC.put(LOG_LEVEL, logLevel);
                chain.doFilter(request, response);
                lastRun = System.nanoTime();
            } finally {
                MDC.clear();
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    boolean accept(long now, long last, long wait) {
        if (wait == 0) {
            return true;
        }
        long nowInSeconds = TimeUnit.NANOSECONDS.toSeconds(now);
        long lastRunInSeconds = TimeUnit.NANOSECONDS.toSeconds(last);
        return (lastRunInSeconds + wait) < nowInSeconds;
    }

    @Override
    public void destroy() {
        // Not used
    }
}
