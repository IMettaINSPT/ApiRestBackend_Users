package com.tp.backend.controller;

import com.tp.backend.dto.sucursal.*;
import com.tp.backend.service.SucursalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    private final SucursalService service;

    public SucursalController(SucursalService service) {
        this.service = service;
    }

    @GetMapping
    public List<SucursalResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public SucursalResponse obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SucursalResponse crear(@Valid @RequestBody SucursalRequest req) {
        return service.crear(req);
    }

    @PutMapping("/{id}")
    public SucursalResponse actualizar(@PathVariable Long id, @Valid @RequestBody SucursalUpdateRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
