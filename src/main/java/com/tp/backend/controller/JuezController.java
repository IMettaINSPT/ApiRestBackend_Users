package com.tp.backend.controller;

import com.tp.backend.dto.juez.*;
import com.tp.backend.service.JuezService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jueces")
public class JuezController {

    private final JuezService service;
    private static final Logger log = LoggerFactory.getLogger(JuezController.class);

    public JuezController(JuezService service) {
        this.service = service;
    }

    @GetMapping
    public List<JuezResponse> listar() {
        log.info("Get /api/jueces/listar ");
        return service.listar();
    }

    @GetMapping("/{id}")
    public JuezResponse obtener(@PathVariable Long id) {
        log.info("Get /api/jueces/Obtener id{}",id);
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JuezResponse crear(@Valid @RequestBody JuezRequest req) {
        log.info("Post /api/jueces/crear claveJuzgado{}", req.getClaveJuzgado());
        return service.crear(req);
    }

    @PutMapping("/{id}")
    public JuezResponse actualizar(@PathVariable Long id, @Valid @RequestBody JuezUpdateRequest req) {
        log.info("put /api/jueces/actualizar id{}",id);
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        log.info("Delete /api/jueces/eliminar id{}",id);
        service.eliminar(id);
    }
}
