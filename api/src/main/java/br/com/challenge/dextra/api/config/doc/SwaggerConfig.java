package br.com.challenge.dextra.api.config.doc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    /**
     * Este método é responsável pela configuração do Swagger, nele, setamos a versão do Swagger,
     * Qual sera o pacote mapeado e quais endpoints serão exibidos.
     * @return Docket
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("br.com.challenge.dextra.api"))
                .paths(PathSelectors.ant("/**")).build();
    }
}


