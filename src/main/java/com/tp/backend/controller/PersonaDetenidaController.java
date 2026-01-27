package com.tp.backend.controller;

import com.tp.backend.dto.personaDetenida.*;
import com.tp.backend.dto.asalto.*;
import com.tp.backend.service.AsaltoService;
import com.tp.backend.service.PersonaDetenidaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personasDetenidas")
public class PersonaDetenidaController {

    private final PersonaDetenidaService service;
    private final AsaltoService asaltoService;
    private static final Logger log = LoggerFactory.getLogger(PersonaDetenidaController.class);

    public PersonaDetenidaController(PersonaDetenidaService service, AsaltoService asaltoService) {
        this.service = service;
        this.asaltoService = asaltoService;
    }

    @GetMapping
    public List<PersonaDetenidaResponse> listar() {
        log.info("Get /api/personasDetenidas/listar");
        return service.listar(); }

    @GetMapping("/{id}")
    public PersonaDetenidaResponse obtener(@PathVariable Long id) {
        log.info("Get /api/personasDetenidas/obtener id{}",id);
        return service.obtener(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonaDetenidaResponse crear(@Valid @RequestBody PersonaDetenidaRequest req) {
        log.info("Post /api/personasDetenidas/crear");
        return service.crear(req); }

    @PutMapping("/{id}")
    public PersonaDetenidaResponse actualizar(@PathVariable Long id, @Valid @RequestBody PersonaDetenidaUpdateRequest req) {
        log.info("Put /api/personasDetenidas/actualizar id{}",id);
        return service.actualizar(id, req);
    }
    @GetMapping("/{id}/asaltos")
    public List<AsaltoResponse> asaltos(@PathVariable Long id) {
        log.info("Get /api/personasDetenidas/listar id{}",id);
        return asaltoService.listarPorPersonaDetenida(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        log.info("Delete /api/personasDetenidas/eliminar id{}",id);
        service.eliminar(id); }
}
