package com.tp.backend.controller;

import com.tp.backend.dto.vigilante.*;
import com.tp.backend.service.VigilanteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vigilantes")
public class VigilanteController {

    private final VigilanteService service;
    private static final Logger log = LoggerFactory.getLogger(VigilanteController.class);

    public VigilanteController(VigilanteService service) {
        this.service = service;
    }

    @GetMapping
    public List<VigilanteResponse> listar() {
        log.info("Get /api/vigilantes/listar");
        return service.listar();
    }

    @GetMapping("/{id}")
    public VigilanteResponse obtener(@PathVariable Long id) {
        log.info("Get /api/vigilantes/obtener id{}",id);
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VigilanteResponse crear(@Valid @RequestBody VigilanteRequest req) {
        log.info("Post /api/vigilantes/crear");
        return service.crear(req);
    }

    @PutMapping("/{id}")
    public VigilanteResponse actualizar(@PathVariable Long id, @Valid @RequestBody VigilanteUpdateRequest req) {
        log.info("Post /api/vigilantes/actualizar id{}",id);
        return service.actualizar(id, req);
    }
    @GetMapping("/disponibles")
    public List<VigilanteResponse> disponibles() {
        log.info("get /api/vigilantes/disponibles");
        return service.listarDisponibles();
    }
    @GetMapping("/disponibles/count")
    public long disponiblesCount() {
        log.info("get /api/vigilantes/disponiblesCount");
        return service.countDisponibles();
    }

// mi perfil
    @GetMapping("/me")
    public VigilanteResponse me(org.springframework.security.core.Authentication auth) {
        log.info("me /api/vigilantes/me");
        return service.obtenerMiPerfil(auth.getName()); // username
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        log.info("Delete /api/vigilantes/eliminar id{}",id);
        service.eliminar(id);
    }
}
