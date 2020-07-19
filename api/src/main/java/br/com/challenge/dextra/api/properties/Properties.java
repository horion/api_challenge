package br.com.challenge.dextra.api.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("app")
public class Properties {

    private String server;


    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
