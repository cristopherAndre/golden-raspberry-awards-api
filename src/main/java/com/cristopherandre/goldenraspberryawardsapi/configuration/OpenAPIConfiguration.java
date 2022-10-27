package com.cristopherandre.goldenraspberryawardsapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

/**
 * @author Cristopher Andre
 */
@Configuration
public class OpenAPIConfiguration {

    @Value("${project.version}")
    private String apiVersion;

    @Value("${project.description}")
    private String apiTitle;

    @Value("${server.servlet.contextPath}")
    private String apiPath;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(apiTitle).version(apiVersion))
                .addServersItem(new Server().url(apiPath));
    }

}
