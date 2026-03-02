package com.tp.backend.juicio.controller;

import com.tp.backend.juicio.application.JuicioUseCase;
import com.tp.backend.juicio.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/juicios")
@CrossOrigin(origins = "*")
public class JuicioController {
    private final JuicioUseCase service;
    public JuicioController(JuicioUseCase service) { this.service = service; }

    @GetMapping
    public List<JuicioResponse> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public JuicioResponse obtener(@PathVariable Long id) { return service.obtener(id); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public JuicioResponse crear(@Valid @RequestBody JuicioRequest req) { return service.crear(req); }

    @PutMapping("/{id}")
    public JuicioResponse actualizar(@PathVariable Long id, @Valid @RequestBody JuicioUpdateRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
