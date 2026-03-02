package com.tp.backend.usuario.controller;

import com.tp.backend.usuario.application.UsuarioUseCase;
import com.tp.backend.usuario.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioUseCase service;
    public UsuarioController(UsuarioUseCase service) { this.service = service; }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'INVESTIGADOR')")
    public List<UsuarioResponse> listar() { return service.listar(); }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INVESTIGADOR')")
    public UsuarioResponse obtener(@PathVariable Long id) { return service.obtener(id); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse crear(@Valid @RequestBody UsuarioRequest req) { return service.crear(req); }

    @PutMapping("/{id}")
    public UsuarioResponse actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }

    @GetMapping("/me")
    public UsuarioResponse me() { return service.obtenerPerfilAutenticado(); }
}