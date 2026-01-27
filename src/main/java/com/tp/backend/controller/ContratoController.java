package com.tp.backend.controller;

import com.tp.backend.dto.contrato.*;
import com.tp.backend.service.ContratoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {

    private final ContratoService service;
    private static final Logger log = LoggerFactory.getLogger(ContratoController.class);

    public ContratoController(ContratoService service) {
        this.service = service;
    }
    @GetMapping
    public List<ContratoResponse> listar() {
        log.info("Get /api/contratos/listar");
        return service.listar();
    }

    @GetMapping("/{id}")
    public ContratoResponse obtener(@PathVariable Long id) {
        log.info("Get /api/contratos/obtener id{}",id);
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContratoResponse crear(@Valid @RequestBody ContratoRequest req) {
        log.info("Post /api/contratos/crear");
        return service.crear(req);
    }

    @PutMapping("/{id}")
    public ContratoResponse actualizar(@PathVariable Long id, @Valid @RequestBody ContratoUpdateRequest req) {
        log.info("Put /api/contratos/actualizar id{}",id);
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        log.info("Delete /api/contratos/eliminar id{}",id);
        service.eliminar(id);
    }

 // busqueda de contratos indirectamente
    @GetMapping("/sucursal/{sucursalId}")
    public List<ContratoResponse> listarPorSucursal(@PathVariable Long sucursalId) {
        log.info("Get /api/contratos/listarPorSucursal sucursalId{}",sucursalId);
        return service.listarPorSucursal(sucursalId);
    }

    @GetMapping("/vigilante/{vigilanteId}")
    public List<ContratoResponse> listarPorVigilante(@PathVariable Long vigilanteId) {
        log.info("Get /api/contratos/listarPorVigilante vigilanteId{}",vigilanteId);
        return service.listarPorVigilante(vigilanteId);
    }
}
