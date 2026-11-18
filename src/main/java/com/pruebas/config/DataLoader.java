package com.pruebas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.pruebas.model.Curso;
import com.pruebas.model.Inscripcion;
import com.pruebas.repository.CursoRepository;
import com.pruebas.repository.InscripcionRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Override
    public void run(String... args) {
        if (cursoRepository.count() == 0) {
            cursoRepository.save(curso("Pruebas de Software", 450.0, 30));
            cursoRepository.save(curso("Seguridad de Aplicaciones", 380.0, 25));
            cursoRepository.save(curso("Base de Datos II", 420.0, 20));
        }
        if (inscripcionRepository.count() == 0) {
            Inscripcion i = new Inscripcion();
            i.setCodigoEstudiante("I2024001");
            i.setCursoId(1L);
            inscripcionRepository.save(i);
        }
    }

    private Curso curso(String nombre, Double precio, Integer vacantes) {
        Curso c = new Curso();
        c.setNombre(nombre);
        c.setPrecio(precio);
        c.setVacantes(vacantes);
        return c;
    }
}
