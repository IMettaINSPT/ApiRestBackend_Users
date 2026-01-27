package com.tp.backend.controller;

import com.tp.backend.dto.asalto.AsaltoResponse;
import com.tp.backend.dto.sucursal.*;
import com.tp.backend.service.AsaltoService;
import com.tp.backend.service.SucursalService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(SucursalController.class);

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
        log.info("Get /api/sucursales/asaltos id{},fecha{}, fechaDesde{}, FechaHasta{}",id,fecha,desde,hasta);
        return asaltoService.listarConFiltros(id, fecha, desde, hasta);
    }


    @GetMapping
    public List<SucursalResponse> listar() {
        log.info("Get /api/sucursales/listar");
        return service.listar();
    }

    @GetMapping("/{id}")
    public SucursalResponse obtener(@PathVariable Long id) {
        log.info("Get /api/sucursales/obtener id{}",id);
        return service.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SucursalResponse crear(@Valid @RequestBody SucursalRequest req) {
        log.info("Post /api/sucursales/crear");
        return service.crear(req);
    }

    @PutMapping("/{id}")
    public SucursalResponse actualizar(@PathVariable Long id, @Valid @RequestBody SucursalUpdateRequest req) {
        log.info("Put /api/sucursales/actualizar id{}",id);
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        log.info("Delete /api/sucursales/eliminar id{}",id);
        service.eliminar(id);
    }
}
