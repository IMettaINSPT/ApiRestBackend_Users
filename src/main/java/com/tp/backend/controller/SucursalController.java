package com.tp.backend.controller;

import com.tp.backend.dto.asalto.AsaltoResponse;
import com.tp.backend.dto.sucursal.*;
import com.tp.backend.service.AsaltoService;
import com.tp.backend.service.SucursalService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    private final SucursalService service;
    private final AsaltoService asaltoService;

    public SucursalController(SucursalService service, AsaltoService asaltoService)
    {
        this.service = service;
        this.asaltoService = asaltoService;
    }

    @GetMapping("/{id}/asaltos")
    public List<AsaltoResponse> asaltosSucursal(
            @PathVariable Long id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) {
        return asaltoService.listarConFiltros(id, fecha, desde, hasta);
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
