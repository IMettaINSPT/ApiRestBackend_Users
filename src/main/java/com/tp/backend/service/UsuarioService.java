package com.tp.backend.service;

import com.tp.backend.dto.*;
import com.tp.backend.exception.BadRequestException;
import com.tp.backend.exception.NotFoundException;
import com.tp.backend.model.*;
import com.tp.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepo;

    public UsuarioService(UsuarioRepository repo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioResponse> listar() {
        return repo.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UsuarioResponse obtenerPorId(Long id) {
        Usuario u = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado (id=" + id + ")"));
        return toResponse(u);
    }

    public UsuarioResponse crear(UsuarioRequest req) {
        validarCreate(req);

        if (repo.existsByUsername(req.username)) {
            throw new BadRequestException("Ya existe un usuario con username='" + req.username + "'");
        }

        Role role = roleRepo.findByNombre(req.rol)
                .orElseThrow(() -> new BadRequestException("Rol inválido: " + req.rol));

        Usuario u = new Usuario(req.username, passwordEncoder.encode(req.password), role);
        Usuario guardado = repo.save(u);
        return toResponse(guardado);
    }

    public UsuarioResponse actualizar(Long id, UsuarioUpdateRequest req) {
        validarUpdate(req);
        Usuario u = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado (id=" + id + ")"));
        if (req.rol != null && !req.rol.isBlank()) {
            Role role = roleRepo.findByNombre(req.rol)
                    .orElseThrow(() -> new BadRequestException("Rol inválido: " + req.rol));
            u.setRole(role);
        }
        // username
        if (req.username != null && !req.username.isBlank() && !req.username.equals(u.getUsername())) {
            if (repo.existsByUsername(req.username)) {
                throw new BadRequestException("Ya existe un usuario con username='" + req.username + "'");
            }
            u.setUsername(req.username);
        }

        // password
        if (req.password != null && !req.password.isBlank()) {
            u.setPassword(passwordEncoder.encode(req.password));
        }



        Usuario guardado = repo.save(u);
        return toResponse(guardado);
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Usuario no encontrado (id=" + id + ")");
        }
        repo.deleteById(id);
    }

    private UsuarioResponse toResponse(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getUsername(), u.getRole().getNombre());
    }

    private void validarCreate(UsuarioRequest req) {
        if (req == null) throw new BadRequestException("Body requerido");
        if (req.username == null || req.username.isBlank()) throw new BadRequestException("username es obligatorio");
        if (req.password == null || req.password.isBlank()) throw new BadRequestException("password es obligatorio");
        if (req.rol == null || req.rol.isBlank()) throw new BadRequestException("rol es obligatorio");
    }

    private void validarUpdate(UsuarioUpdateRequest req) {
        if (req == null) throw new BadRequestException("Body requerido");

        boolean allEmpty =
                (req.username == null || req.username.isBlank()) &&
                        (req.password == null || req.password.isBlank()) &&
                        (req.rol == null || req.rol.isBlank());

        if (allEmpty) throw new BadRequestException("Debe enviarse al menos un campo para actualizar");
    }
}
