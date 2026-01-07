package com.tp.backend.controller;

import com.tp.backend.dto.juez.*;
import com.tp.backend.service.JuezService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jueces")
public class JuezController {

    private final JuezService service;

    public JuezController(JuezService service) {
        this.service = service;
    }

    @GetMapping
    public List<JuezResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public JuezResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JuezResponse crear(@Valid @RequestBody JuezRequest req) {
        return service.crear(req);
    }

    @PutMapping("/{id}")
    public JuezResponse actualizar(@PathVariable Long id, @Valid @RequestBody JuezUpdateRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
