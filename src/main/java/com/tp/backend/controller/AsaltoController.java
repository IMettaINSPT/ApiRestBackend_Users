package com.tp.backend.controller;

import com.tp.backend.dto.asalto.*;
import com.tp.backend.service.AsaltoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/asaltos")
public class AsaltoController {

    private final AsaltoService service;

    public AsaltoController(AsaltoService service) {
        this.service = service;
    }

    @GetMapping
    public List<AsaltoResponse> listar(
            @RequestParam(required = false) Long sucursalId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) {
        return service.listarConFiltros(sucursalId, fecha, desde, hasta);
    }

    @GetMapping("/{id}")
    public AsaltoResponse porId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public AsaltoResponse crear(@RequestBody AsaltoRequest req) {
        return service.crear(req);
    }

    @PutMapping("/{id}")
    public AsaltoResponse actualizar(@PathVariable Long id, @RequestBody AsaltoRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
