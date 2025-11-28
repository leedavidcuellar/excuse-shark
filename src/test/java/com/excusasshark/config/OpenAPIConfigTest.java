package com.excusasshark.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OpenAPIConfig Tests")
class OpenAPIConfigTest {

    @Test
    @DisplayName("customOpenAPI debe construir objeto con metadata esperada")
    void customOpenAPIMetadata() {
        OpenAPIConfig config = new OpenAPIConfig();
        OpenAPI api = config.customOpenAPI();
        assertNotNull(api.getInfo());
        assertEquals("Excusas Shark API", api.getInfo().getTitle());
        assertEquals("1.0.0", api.getInfo().getVersion());
        assertNotNull(api.getInfo().getContact());
        assertEquals("Lee Cuellar", api.getInfo().getContact().getName());
        assertNotNull(api.getInfo().getLicense());
        assertEquals("MIT License", api.getInfo().getLicense().getName());
    }
}
