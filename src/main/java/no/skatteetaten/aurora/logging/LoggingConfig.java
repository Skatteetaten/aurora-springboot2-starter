package no.skatteetaten.aurora.logging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

    private final Integer requestWaitTimeSeconds;

    public LoggingConfig(@Value("${aurora.starter.requestbasedlogging.wait:30}") Integer requestWaitTimeSeconds) {
        this.requestWaitTimeSeconds = requestWaitTimeSeconds;
    }

    @Bean
    @ConditionalOnProperty(prefix = "aurora.starter.requestbasedlogging", name = "enabled")
    public FilterRegistrationBean registerFilters() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.addUrlPatterns("/*");
        registration.setFilter(new RequestResponseLogFilter());
        registration.setFilter(new MdcRequestFilter(requestWaitTimeSeconds));
        return registration;
    }

}
