package com.tp.backend.sucursal.controller;

import com.tp.backend.sucursal.application.SucursalUseCase;
import com.tp.backend.sucursal.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {
    private final SucursalUseCase service;
    public SucursalController(SucursalUseCase service) { this.service = service; }

    @GetMapping
    public List<SucursalResponse> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public SucursalResponse obtener(@PathVariable Long id) { return service.obtener(id); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public SucursalResponse crear(@Valid @RequestBody SucursalRequest req) { return service.crear(req); }

    @PutMapping("/{id}")
    public SucursalResponse actualizar(@PathVariable Long id, @Valid @RequestBody SucursalRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }

    @GetMapping("/banco/{bancoId}")
    public List<SucursalResponse> listarPorBanco(@PathVariable Long bancoId) {
        return service.listarPorBanco(bancoId);
    }
}