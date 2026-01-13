package com.tp.backend.controller;

import com.tp.backend.dto.vigilante.*;
import com.tp.backend.service.VigilanteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vigilantes")
public class VigilanteController {

    private final VigilanteService service;

    public VigilanteController(VigilanteService service) {
        this.service = service;
    }

    @GetMapping
    public List<VigilanteResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public VigilanteResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VigilanteResponse crear(@Valid @RequestBody VigilanteRequest req) {
        return service.crear(req);
    }

    @PutMapping("/{id}")
    public VigilanteResponse actualizar(@PathVariable Long id, @Valid @RequestBody VigilanteUpdateRequest req) {
        return service.actualizar(id, req);
    }
    @GetMapping("/disponibles")
    public List<VigilanteResponse> disponibles() {
        return service.listarDisponibles();
    }
    @GetMapping("/disponibles/count")
    public long disponiblesCount() {
        return service.countDisponibles();
    }


    @GetMapping("/me")
    public VigilanteResponse me(org.springframework.security.core.Authentication auth) {
        return service.obtenerMiPerfil(auth.getName()); // username
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
