package com.tp.backend.banco.controller;

import com.tp.backend.banco.application.BancoUseCase;
import com.tp.backend.banco.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bancos")
public class BancoController {
    private final BancoUseCase service;

    public BancoController(BancoUseCase service) { this.service = service; }

    @GetMapping
    public List<BancoResponse> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public BancoResponse obtener(@PathVariable Long id) { return service.obtener(id); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public BancoResponse crear(@Valid @RequestBody BancoRequest req) { return service.crear(req); }

    @PutMapping("/{id}")
    public BancoResponse actualizar(@PathVariable Long id, @Valid @RequestBody BancoRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}