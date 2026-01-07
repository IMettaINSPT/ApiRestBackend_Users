package com.tp.backend.controller;

import com.tp.backend.dto.banco.*;
import com.tp.backend.service.BancoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bancos")
public class BancoController {

    private final BancoService service;

    public BancoController(BancoService service) {
        this.service = service;
    }

    @GetMapping
    public List<BancoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public BancoResponse obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BancoResponse crear(@Valid @RequestBody BancoRequest req) {
        return service.crear(req);
    }

    @PutMapping("/{id}")
    public BancoResponse actualizar(@PathVariable Long id, @Valid @RequestBody BancoUpdateRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
