package com.tp.backend.banda.controller;

import com.tp.backend.banda.application.BandaUseCase;
import com.tp.backend.banda.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bandas")
public class BandaController {
    private final BandaUseCase service;

    public BandaController(BandaUseCase service) { this.service = service; }

    @GetMapping
    public List<BandaResponse> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public BandaResponse obtener(@PathVariable Long id) { return service.obtener(id); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public BandaResponse crear(@Valid @RequestBody BandaRequest req) { return service.crear(req); }

    @PutMapping("/{id}")
    public BandaResponse actualizar(@PathVariable Long id, @Valid @RequestBody BandaUpdateRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}