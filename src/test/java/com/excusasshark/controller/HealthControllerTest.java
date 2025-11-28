package com.excusasshark.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static com.excusasshark.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para HealthController
 * Verifica el endpoint de health check
 */
@DisplayName("HealthController Unit Tests")
class HealthControllerTest {

    private HealthController healthController;

    @BeforeEach
    void setUp() {
        healthController = new HealthController();
    }

    @Test
    @DisplayName("GET /health debe retornar 200 con status UP")
    void testHealth_shouldReturn200WithStatusUp() {
        // Act
        ResponseEntity<Map<String, Object>> response = healthController.health();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(UP, response.getBody().get(STATUS));
        assertEquals(TEST_EXCUSAS_SHARK_API, response.getBody().get(SERVICE));
        assertEquals(VERSION_1_0_0, response.getBody().get(VERSION));
        assertTrue(response.getBody().containsKey(TIMESTAMP));
    }

    @Test
    @DisplayName("GET /health debe incluir timestamp válido")
    void testHealth_shouldIncludeValidTimestamp() {
        // Act
        ResponseEntity<Map<String, Object>> response = healthController.health();

        // Assert
        assertNotNull(response.getBody());
        Object timestamp = response.getBody().get(TIMESTAMP);
        assertNotNull(timestamp);
        assertTrue(timestamp instanceof Long);
        assertTrue((Long) timestamp > 0);
    }

    @Test
    @DisplayName("GET /health debe retornar el mismo formato en múltiples llamadas")
    void testHealth_shouldReturnConsistentFormat() {
        // Act
        ResponseEntity<Map<String, Object>> response1 = healthController.health();
        ResponseEntity<Map<String, Object>> response2 = healthController.health();

        // Assert
        assertNotNull(response1.getBody());
        assertNotNull(response2.getBody());
        assertEquals(response1.getBody().get(STATUS), response2.getBody().get(STATUS));
        assertEquals(response1.getBody().get(SERVICE), response2.getBody().get(SERVICE));
        assertEquals(response1.getBody().get(VERSION), response2.getBody().get(VERSION));
    }
}
