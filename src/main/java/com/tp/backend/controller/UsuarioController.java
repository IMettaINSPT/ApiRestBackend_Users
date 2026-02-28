package com.tp.backend.controller;

import com.tp.backend.dto.usuario.*;
import com.tp.backend.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'INVESTIGADOR')")
    public List<UsuarioResponse> listar() {
        log.info("Get /api/usuarios/listar");
        return service.listar();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INVESTIGADOR')")
    public UsuarioResponse obtener(@PathVariable Long id) {
        log.info("Get /api/usuarios/obtener id{}",id);
        return service.obtenerPorId(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse crear(@Valid @RequestBody UsuarioRequest req) {
        log.info("Post /api/usuarios/crear");
        return service.crear(req);
    }

    @PutMapping("/{id}")
    public UsuarioResponse actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateRequest req) {
        log.info("Put /api/usuarios/actualizar id{}",id);
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        log.info("Delete /api/usuarios/eliminar id{}",id);
        service.eliminar(id);
    }

    @GetMapping("/me")
    public UsuarioResponse obtenerPerfilPropio() {
        log.info("Get /api/usuarios/me - Recuperando perfil del token");
        // El service debe obtener el usuario desde el SecurityContext (el token logueado)
        return service.obtenerPerfilAutenticado();
    }

}