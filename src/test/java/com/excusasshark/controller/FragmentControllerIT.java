package com.excusasshark.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests de integración para FragmentController
 * Verifica CRUD completo de fragmentos
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("FragmentController Integration Tests")
class FragmentControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "";
    }

    @Test
    @DisplayName("GET /api/fragments debe retornar 200 con lista")
    void testGetAllFragments() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/api/fragments")
        .then()
                .statusCode(200)
                .body("", hasItems());
    }

    @Test
    @DisplayName("GET /api/fragments/active debe retornar 200 con fragmentos activos")
    void testGetActiveFragments() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/api/fragments/active")
        .then()
                .statusCode(200)
                .body("", hasItems());
    }

    @Test
    @DisplayName("GET /api/fragments/by-type?tipo=CONTEXTO debe retornar 200")
    void testGetFragmentsByType() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("tipo", "CONTEXTO")
        .when()
                .get("/api/fragments/by-type")
        .then()
                .statusCode(200)
                .body("", hasItems());
    }

    @Test
    @DisplayName("POST /api/fragments debe crear fragmento y retornar 201")
    void testCreateFragment() {
        String requestBody = """
                {
                    "type": "CONTEXTO",
                    "text": "Test fragment context",
                    "source": "TEST",
                    "category": "testing"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
        .when()
                .post("/api/fragments")
        .then()
                .statusCode(201)
                .body("type", equalTo("CONTEXTO"))
                .body("text", equalTo("Test fragment context"));
    }

    @Test
    @DisplayName("POST /api/fragments con datos inválidos debe retornar 400")
    void testCreateFragmentInvalid() {
        String requestBody = """
                {
                    "type": "CONTEXTO",
                    "text": "",
                    "source": "TEST"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
        .when()
                .post("/api/fragments")
        .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("DELETE /api/fragments/{id} debe retornar 204")
    void testDeleteFragment() {
        // Primero crear un fragmento
        String createBody = """
                {
                    "type": "CAUSA",
                    "text": "Fragment to delete",
                    "source": "TEST",
                    "category": "testing"
                }
                """;

        Long fragmentId = given()
                .contentType(ContentType.JSON)
                .body(createBody)
        .when()
                .post("/api/fragments")
        .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Luego eliminar
        given()
                .contentType(ContentType.JSON)
        .when()
                .delete("/api/fragments/" + fragmentId)
        .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("GET /api/fragments/{id} debe retornar 200 si existe")
    void testGetFragmentById() {
        // Crear primero
        String createBody = """
                {
                    "type": "CONSECUENCIA",
                    "text": "Fragment to fetch",
                    "source": "TEST",
                    "category": "testing"
                }
                """;

        Long fragmentId = given()
                .contentType(ContentType.JSON)
                .body(createBody)
        .when()
                .post("/api/fragments")
        .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Luego obtener
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/api/fragments/" + fragmentId)
        .then()
                .statusCode(200)
                .body("id", equalTo(fragmentId.intValue()));
    }

    @Test
    @DisplayName("GET /api/fragments/{id} debe retornar 404 si no existe")
    void testGetFragmentByIdNotFound() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/api/fragments/99999")
        .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("PUT /api/fragments/{id} debe actualizar y retornar 200")
    void testUpdateFragment() {
        // Crear primero
        String createBody = """
                {
                    "type": "RECOMENDACION",
                    "text": "Original text",
                    "source": "TEST",
                    "category": "testing"
                }
                """;

        Long fragmentId = given()
                .contentType(ContentType.JSON)
                .body(createBody)
        .when()
                .post("/api/fragments")
        .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Actualizar
        String updateBody = """
                {
                    "type": "RECOMENDACION",
                    "text": "Updated text",
                    "source": "TEST-UPDATE",
                    "category": "testing"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(updateBody)
        .when()
                .put("/api/fragments/" + fragmentId)
        .then()
                .statusCode(200)
                .body("text", equalTo("Updated text"));
    }
}
