package com.tp.backend.controller;

import com.tp.backend.dto.asalto.*;
import com.tp.backend.service.AsaltoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/asaltos")
public class AsaltoController {

    private final AsaltoService service;

    public AsaltoController(AsaltoService service) {
        this.service = service;
    }

    @GetMapping
    public List<AsaltoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public AsaltoResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AsaltoResponse crear(@Valid @RequestBody AsaltoRequest req) {
        return service.crear(req);
    }

    @PutMapping("/{id}")
    public AsaltoResponse actualizar(@PathVariable Long id,
                                     @Valid @RequestBody AsaltoUpdateRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
