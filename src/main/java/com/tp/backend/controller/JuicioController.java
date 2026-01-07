package com.tp.backend.controller;

import com.tp.backend.dto.juicio.*;
import com.tp.backend.service.JuicioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/juicios")
public class JuicioController {

    private final JuicioService service;

    public JuicioController(JuicioService service) {
        this.service = service;
    }

    @GetMapping
    public List<JuicioResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public JuicioResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JuicioResponse crear(@Valid @RequestBody JuicioRequest req) {
        return service.crear(req);
    }

    @PutMapping("/{id}")
    public JuicioResponse actualizar(@PathVariable Long id,
                                     @Valid @RequestBody JuicioUpdateRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
