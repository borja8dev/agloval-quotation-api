package com.agloval.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI quotationApiDocs() {
        return new OpenAPI()
                .info(new Info()
                        .title("Agloval Quotation API")
                        .version("0.2.0")
                        .description("REST API for quotation management — Agloval SL Portfolio MVP")
                        .contact(new Contact()
                                .name("Agloval SL")
                                .email("borja.rodriguez789@gmail.com")));
    }
}
