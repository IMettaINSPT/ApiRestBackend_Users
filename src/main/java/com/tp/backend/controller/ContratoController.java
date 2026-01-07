package com.tp.backend.controller;

import com.tp.backend.dto.contrato.*;
import com.tp.backend.service.ContratoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {

    private final ContratoService service;

    public ContratoController(ContratoService service) {
        this.service = service;
    }
    @GetMapping
    public List<ContratoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ContratoResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContratoResponse crear(@Valid @RequestBody ContratoRequest req) {
        return service.crear(req);
    }

    @PutMapping("/{id}")
    public ContratoResponse actualizar(@PathVariable Long id, @Valid @RequestBody ContratoUpdateRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

 // busqueda de contratos indirectamente
    @GetMapping("/sucursal/{sucursalId}")
    public List<ContratoResponse> listarPorSucursal(@PathVariable Long sucursalId) {
        return service.listarPorSucursal(sucursalId);
    }

    @GetMapping("/vigilante/{vigilanteId}")
    public List<ContratoResponse> listarPorVigilante(@PathVariable Long vigilanteId) {
        return service.listarPorVigilante(vigilanteId);
    }
}
