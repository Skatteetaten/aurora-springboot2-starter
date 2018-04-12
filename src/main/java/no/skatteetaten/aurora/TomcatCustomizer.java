package no.skatteetaten.aurora;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(name = "org.apache.catalina.startup.Tomcat")
public class TomcatCustomizer {

    @Bean
    public GracefulShutdown gs() {
        return new GracefulShutdown();
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer(GracefulShutdown gs) {
        return server -> server.addConnectorCustomizers(gs);
    }

}
