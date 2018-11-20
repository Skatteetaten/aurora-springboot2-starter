package no.skatteetaten.aurora.logging;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestResponseLogFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Not used
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        if (logger.isDebugEnabled() || logger.isTraceEnabled()) {
            if (request instanceof HttpServletRequest) {
                StringBuffer requestURL = ((HttpServletRequest) request).getRequestURL();
                String queryString = ((HttpServletRequest) request).getQueryString();
                String method = ((HttpServletRequest) request).getMethod();
                logger.debug("{} - {}{}", method, requestURL, queryString == null ? "" : queryString);
            }
            chain.doFilter(request, response);
            if (response instanceof HttpServletResponse) {
                int status = ((HttpServletResponse) response).getStatus();
                logger.debug("Http response status {}", status);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Not used
    }
}
