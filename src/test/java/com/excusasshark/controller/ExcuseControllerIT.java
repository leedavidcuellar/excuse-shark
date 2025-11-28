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
 * Tests de integración para ExcuseController
 * Verifica todos los endpoints REST
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("ExcuseController Integration Tests")
class ExcuseControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "";
    }

    @Test
    @DisplayName("GET /api/excuses/random debe retornar 200 con excusa")
    void testGetRandomExcuse() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/api/excuses/random")
        .then()
                .statusCode(200)
                .body("contexto", notNullValue())
                .body("causa", notNullValue())
                .body("consecuencia", notNullValue())
                .body("recomendacion", notNullValue());
    }

    @Test
    @DisplayName("GET /api/excuses/daily debe retornar 200 con excusa del día")
    void testGetDailyExcuse() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/api/excuses/daily")
        .then()
                .statusCode(200)
                .body("contexto", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    @DisplayName("GET /api/excuses/meme debe retornar 200 con meme")
    void testGetExcuseWithMeme() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/api/excuses/meme")
        .then()
                .statusCode(200)
                .body("meme", notNullValue());
    }

    @Test
    @DisplayName("GET /api/excuses/law debe retornar 200 con ley")
    void testGetExcuseWithLaw() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/api/excuses/law")
        .then()
                .statusCode(200)
                .body("ley", notNullValue());
    }

    @Test
    @DisplayName("GET /api/excuses/ultra debe retornar 200 UltraShark")
    void testGetUltraSharkExcuse() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/api/excuses/ultra")
        .then()
                .statusCode(200)
                .body("excusa", notNullValue())
                .body("meme", notNullValue())
                .body("ley", notNullValue());
    }

    @Test
    @DisplayName("GET /api/excuses/role/DEV debe retornar 200 con excusa para DEV")
    void testGetExcuseForRoleDev() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/api/excuses/role/DEV")
        .then()
                .statusCode(200)
                .body("roleTarget", equalTo("DEV"));
    }

    @Test
    @DisplayName("GET /api/excuses/role/DEVOPS debe retornar 200")
    void testGetExcuseForRoleDevOps() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/api/excuses/role/DEVOPS")
        .then()
                .statusCode(200)
                .body("roleTarget", equalTo("DEVOPS"));
    }

    @Test
    @DisplayName("GET /api/excuses/role/QA debe retornar 200")
    void testGetExcuseForRoleQA() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/api/excuses/role/QA")
        .then()
                .statusCode(200)
                .body("roleTarget", equalTo("QA"));
    }

    @Test
    @DisplayName("GET /api/excuses/role/INVALID debe retornar 400")
    void testGetExcuseForInvalidRole() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/api/excuses/role/INVALID_ROLE")
        .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("GET /api/excuses debe retornar 200 con lista")
    void testGetAllExcuses() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/api/excuses")
        .then()
                .statusCode(200)
                .body("", hasItems());
    }

    @Test
    @DisplayName("GET /health debe retornar 200 UP")
    void testHealthCheck() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/health")
        .then()
                .statusCode(200)
                .body("status", equalTo("UP"))
                .body("service", equalTo("Excusas Shark API"));
    }
}
