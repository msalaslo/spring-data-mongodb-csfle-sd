package com.verisure.vcp.springdatamongodbcsfle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * OpenApiConfiguration configuration.  API documentation is available at /swagger-ui.html
 *
 * @since 1.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
@Configuration
public class OpenAPIConfiguration {

    // TODO (FaaS) Change in order to adapt to the actual microservice REST API.
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes("basicScheme",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("basic")
                                ))
                .info(new Info()
                        .title("Application REST API")
                        .description("Application manager REST API documentation.")
                        .version("0.0.1-SNAPSHOT")
                        .contact(new Contact().email("faas@securitasdirect.es").name("FaaS Securitas Direct").url("https://faas.securitasdirect.local/"))
                        .license(new License().name("License of API for YourCompany use only").url("License of API for YourCompany use only"))
                );
    }
}
