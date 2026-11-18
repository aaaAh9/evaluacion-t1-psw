package com.pruebas.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

@DisplayName("Arturo Huaman Sota - 202330687 - CursoApiTest")
class CursoApiTest {

    @BeforeAll
    static void configurar() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    @DisplayName("Escenario 1: consultar todos los cursos")
    void consultarTodosLosCursos() {
        given()
            .when()
                .get("/api/cursos")
            .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("Escenario 2: consultar el curso con id 1")
    void consultarCursoPorId() {
        var respuesta = given()
            .when()
                .get("/api/cursos/1")
            .then()
                .statusCode(200)
                .body("nombre", equalTo("Pruebas de Software"))
                .extract();

        double precio = respuesta.jsonPath().getDouble("precio");
        assertThat(precio, greaterThan(0.0));
    }
}
