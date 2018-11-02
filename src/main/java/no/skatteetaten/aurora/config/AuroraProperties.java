package no.skatteetaten.aurora.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aurora.starter")
public class AuroraProperties {

    private Headerfilter headerfilter;

    public Headerfilter getHeaderfilter() {
        return headerfilter;
    }

    public void setHeaderfilter(Headerfilter headerfilter) {
        this.headerfilter = headerfilter;
    }

    public static class Headerfilter {
        /*
         * Set to false to turn of header filter
         */
        private boolean enabled;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

}
