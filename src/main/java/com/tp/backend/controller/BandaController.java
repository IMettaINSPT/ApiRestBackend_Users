package com.tp.backend.controller;

import com.tp.backend.dto.banda.*;
import com.tp.backend.service.BandaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/api/bandas")
public class BandaController {

    private final BandaService service;
    private static final Logger log = LoggerFactory.getLogger(BandaController.class);

    public BandaController(BandaService service) {
        this.service = service;
    }

    @GetMapping
    public List<BandaResponse> listar() {
        log.info("Get /api/bandas/Listar");
        return service.listar(); }

    @GetMapping("/{id}")
    public BandaResponse obtener(@PathVariable Long id) {
        log.info("Get /api/bandas/Obtener id{}",id);
        return service.obtener(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BandaResponse crear(@Valid @RequestBody BandaRequest req) {
        log.info("Post /api/bandas/Crear nombre{}", req.getNombre());

        return service.crear(req); }

    @PutMapping("/{id}")
    public BandaResponse actualizar(@PathVariable Long id, @Valid @RequestBody BandaUpdateRequest req) {
        log.info("Put /api/bandas/actualizar id{}",id);
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        log.info("Delete /api/bandas/eliminar id{}",id);
        service.eliminar(id); }
}
