package com.tp.backend.contrato.controller;

import com.tp.backend.contrato.application.ContratoUseCase;
import com.tp.backend.contrato.dto.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {
    private final ContratoUseCase service; // Ahora inyectamos la interfaz
    private static final Logger log = LoggerFactory.getLogger(ContratoController.class);

    public ContratoController(ContratoUseCase service) { this.service = service; }

    @GetMapping
    public List<ContratoResponse> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public ContratoResponse obtener(@PathVariable Long id) { return service.obtener(id); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public ContratoResponse crear(@Valid @RequestBody ContratoRequest req) { return service.crear(req); }

    @PutMapping("/{id}")
    public ContratoResponse actualizar(@PathVariable Long id, @Valid @RequestBody ContratoUpdateRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }

    @GetMapping("/sucursal/{sucursalId}")
    public List<ContratoResponse> listarPorSucursal(@PathVariable Long sucursalId) {
        return service.listarPorSucursal(sucursalId);
    }

    @GetMapping("/vigilante/{vigilanteId}")
    public List<ContratoResponse> listarPorVigilante(@PathVariable Long vigilanteId) {
        return service.listarPorVigilante(vigilanteId);
    }
}
