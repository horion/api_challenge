package br.com.challenge.dextra.api.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * A classe Properties, é responsável por fazer um bind entre o application.properties e o código fonte
 * de forma programática. Para dizermos qual o grupo de propriedades que queremos acessar, devemos informar
 * na anotação ConfigurationProperties("app")
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("app")
public class Properties {

    private String server;


    /**
     * @return URL do Potter API
     * Quando esse método é invocado, ele retorna o valor da propriedade app.server, que neste caso é a URL do Potter API
     */
    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
