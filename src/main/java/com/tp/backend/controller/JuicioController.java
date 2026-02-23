package com.tp.backend.controller;

import com.tp.backend.dto.juicio.JuicioRequest;
import com.tp.backend.dto.juicio.JuicioResponse;
import com.tp.backend.dto.juicio.JuicioUpdateRequest;
import com.tp.backend.service.JuicioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// RESTAURADO: Ahora el Frontend podrá encontrar este recurso
@RequestMapping("/api/juicios")
public class JuicioController {

    private final JuicioService service;
    private static final Logger log = LoggerFactory.getLogger(JuicioController.class);

    public JuicioController(JuicioService service) {
        this.service = service;
    }

    @GetMapping
    public List<JuicioResponse> listar() {
        log.info("GET /api/juicios");
        return service.listar();
    }

    @GetMapping("/{id}")
    public JuicioResponse obtener(@PathVariable Long id) {
        log.info("GET /api/juicios/{}", id);
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JuicioResponse crear(@Valid @RequestBody JuicioRequest req) {
        log.info("POST /api/juicios - Data: {}", req);
        return service.crear(req);
    }

    @PutMapping("/{id}")
    public JuicioResponse actualizar(@PathVariable Long id,
                                     @Valid @RequestBody JuicioUpdateRequest req) {
        log.info("PUT /api/juicios/{} - Data: {}", id, req);
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        log.info("DELETE /api/juicios/{}", id);
        service.eliminar(id);
    }
}