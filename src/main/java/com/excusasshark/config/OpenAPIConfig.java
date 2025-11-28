package com.excusasshark.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI/Swagger
 * Define la documentación automática de la API REST
 */
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Excusas Shark API")
                        .version("1.0.0")
                        .description("🦈 API para generar excusas técnicas argentinas. " +
                                "Del Mojarrita (simple) al White Shark (ULTRA con meme + ley)")
                        .contact(new Contact()
                                .name("Lee Cuellar")
                                .url("https://github.com/lee-cuellar-acc")
                                .email("lee.cuellar@example.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
