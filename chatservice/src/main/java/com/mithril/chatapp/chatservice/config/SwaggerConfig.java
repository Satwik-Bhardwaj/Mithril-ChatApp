package com.mithril.chatapp.chatservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mithril Chat Application - Chat Service")
                        .description("Mithril Chat App API doc for Chat Service")
                        .version("1.0"));
    }
}
