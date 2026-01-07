package com.tp.backend.controller;

import com.tp.backend.dto.personaDetenida.*;
import com.tp.backend.service.PersonaDetenidaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personasDetenidas")
public class PersonaDetenidaController {

    private final PersonaDetenidaService service;

    public PersonaDetenidaController(PersonaDetenidaService service) {
        this.service = service;
    }

    @GetMapping
    public List<PersonaDetenidaResponse> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public PersonaDetenidaResponse obtener(@PathVariable Long id) { return service.obtener(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonaDetenidaResponse crear(@Valid @RequestBody PersonaDetenidaRequest req) { return service.crear(req); }

    @PutMapping("/{id}")
    public PersonaDetenidaResponse actualizar(@PathVariable Long id, @Valid @RequestBody PersonaDetenidaUpdateRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
