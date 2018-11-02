package no.skatteetaten.aurora.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aurora.starter")
public class AuroraProperties {

    /*
     * Set to false to turn of header filter
     */
    private boolean headerfilter;

     /*
     * Set to false to turn of header filter
     */
    public boolean isHeaderfilter() {
        return headerfilter;
    }

    public void setHeaderfilter(boolean headerfilter) {
        this.headerfilter = headerfilter;
    }
}
