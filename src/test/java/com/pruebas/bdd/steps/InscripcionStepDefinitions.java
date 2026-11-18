package com.pruebas.bdd.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class InscripcionStepDefinitions {

    private String codigoEstudiante;
    private Response respuesta;

    @Given("un estudiante nuevo con un código distinto en cada ejecución")
    public void estudianteNuevoConCodigoDistinto() {
        codigoEstudiante = "I" + System.currentTimeMillis();
    }

    @Given("el estudiante {string} ya está inscrito en el curso {long}")
    public void estudianteYaInscrito(String codigo, Long cursoId) {
        codigoEstudiante = codigo;
    }

    @When("el estudiante se inscribe en el curso {long}")
    public void estudianteSeInscribe(Long cursoId) {
        respuesta = inscribir(codigoEstudiante, cursoId);
    }

    @When("el estudiante intenta inscribirse de nuevo en el curso {long}")
    public void estudianteIntentaInscribirseDeNuevo(Long cursoId) {
        respuesta = inscribir(codigoEstudiante, cursoId);
    }

    @Then("el sistema responde con el código {int}")
    public void elSistemaRespondeConElCodigo(Integer codigo) {
        assertEquals(codigo.intValue(), respuesta.getStatusCode());
    }

    @Then("el sistema rechaza la inscripción con un código de error 4xx o 5xx")
    public void elSistemaRechazaLaInscripcion() {
        int codigo = respuesta.getStatusCode();
        assertTrue(codigo >= 400 && codigo <= 599);
    }

    private Response inscribir(String codigo, Long cursoId) {
        String cuerpo = "{\"codigoEstudiante\":\"" + codigo + "\",\"cursoId\":" + cursoId + "}";
        return RestAssured.given()
                .baseUri("http://localhost")
                .port(8080)
                .contentType(ContentType.JSON)
                .body(cuerpo)
                .when()
                .post("/api/inscripciones");
    }
}
