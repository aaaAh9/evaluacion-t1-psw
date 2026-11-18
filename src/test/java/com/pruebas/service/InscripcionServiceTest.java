package com.pruebas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pruebas.model.Inscripcion;
import com.pruebas.model.services.InscripcionService;
import com.pruebas.repository.InscripcionRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Arturo Huaman Sota - 202330687 - InscripcionServiceTest")
class InscripcionServiceTest {

    @Mock
    private InscripcionRepository repository;

    @InjectMocks
    private InscripcionService service;

    @Test
    @DisplayName("Caso 1: inscribir a un estudiante correctamente")
    void inscribirEstudianteCorrectamente() {
        Inscripcion nueva = crear(null, "I2024050", 2L);
        Inscripcion guardada = crear(10L, "I2024050", 2L);
        when(repository.existsByCodigoEstudianteAndCursoId("I2024050", 2L)).thenReturn(false);
        when(repository.save(any(Inscripcion.class))).thenReturn(guardada);

        Inscripcion resultado = service.inscribir(nueva);

        ArgumentCaptor<Inscripcion> captor = ArgumentCaptor.forClass(Inscripcion.class);
        verify(repository, times(1)).save(captor.capture());
        assertEquals("I2024050", captor.getValue().getCodigoEstudiante());
        assertEquals(2L, captor.getValue().getCursoId());
        assertNotNull(resultado);
        assertEquals("I2024050", resultado.getCodigoEstudiante());
    }

    @Test
    @DisplayName("Caso 2: inscribir a un estudiante que ya esta inscrito")
    void inscribirEstudianteYaInscrito() {
        Inscripcion existente = crear(null, "I2024001", 1L);
        when(repository.existsByCodigoEstudianteAndCursoId("I2024001", 1L)).thenReturn(true);

        RuntimeException excepcion = assertThrows(RuntimeException.class,
                () -> service.inscribir(existente));

        assertEquals("Estudiante ya inscrito", excepcion.getMessage());
        verify(repository, never()).save(any(Inscripcion.class));
    }

    @Test
    @DisplayName("Caso 3: listar las inscripciones de un estudiante")
    void listarInscripcionesPorEstudiante() {
        List<Inscripcion> inscripciones = List.of(
                crear(1L, "I2024001", 1L),
                crear(2L, "I2024001", 2L));
        when(repository.findByCodigoEstudiante("I2024001")).thenReturn(inscripciones);

        List<Inscripcion> resultado = service.listarPorEstudiante("I2024001");

        assertEquals(2, resultado.size());
        resultado.forEach(i -> assertEquals("I2024001", i.getCodigoEstudiante()));
        verify(repository, times(1)).findByCodigoEstudiante("I2024001");
    }

    private Inscripcion crear(Long id, String codigoEstudiante, Long cursoId) {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId(id);
        inscripcion.setCodigoEstudiante(codigoEstudiante);
        inscripcion.setCursoId(cursoId);
        return inscripcion;
    }
}
