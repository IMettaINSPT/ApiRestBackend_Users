package com.tp.backend.controller;

import com.tp.backend.dto.juicio.*;
import com.tp.backend.service.JuicioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/juicios")
public class JuicioController {

    private final JuicioService service;
    private static final Logger log = LoggerFactory.getLogger(JuicioController.class);

    public JuicioController(JuicioService service) {
        this.service = service;
    }

    @GetMapping
    public List<JuicioResponse> listar() {
        log.info("Get /api/juicios/listar");
        return service.listar();
    }

    @GetMapping("/{id}")
    public JuicioResponse obtener(@PathVariable Long id) {
        log.info("Get /api/juicios/obtener id{}",id);
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JuicioResponse crear(@Valid @RequestBody JuicioRequest req) {
        log.info("Posts /api/juicios/crear");
        return service.crear(req);
    }

    @PutMapping("/{id}")
    public JuicioResponse actualizar(@PathVariable Long id,
                                     @Valid @RequestBody JuicioUpdateRequest req) {
        log.info("put /api/juicios/actualizar id{}",id);
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        log.info("Delete /api/juicios/eliminar id{}",id);
        service.eliminar(id);
    }
}
