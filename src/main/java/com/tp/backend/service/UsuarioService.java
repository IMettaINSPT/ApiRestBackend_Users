package com.tp.backend.service;

import com.tp.backend.dto.usuario.UsuarioRequest;
import com.tp.backend.dto.usuario.UsuarioResponse;
import com.tp.backend.dto.usuario.UsuarioUpdateRequest;
import com.tp.backend.exception.BadRequestException;
import com.tp.backend.exception.NotFoundException;
import com.tp.backend.model.*;
import com.tp.backend.repository.UsuarioRepository;
import com.tp.backend.repository.VigilanteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final VigilanteRepository vigilanteRepository;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder, VigilanteRepository vigilanteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.vigilanteRepository = vigilanteRepository;
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponse obtenerPorId(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + id));
        return toResponse(u);
    }


    public UsuarioResponse crear(UsuarioRequest req) {
        if (usuarioRepository.existsByUsername(req.getUsername())) {
            throw new BadRequestException("Ya existe un usuario con username: " + req.getUsername());
        }

        Usuario u = crearSegunTipo(req.getTipo()); // ADMIN / INVESTIGADOR / VIGILANTE

            if (u instanceof UsuarioVigilante uv) {
                if (req.getVigilanteId() == null) {
                    throw new BadRequestException("Para VIGILANTE se requiere vigilanteId");
                }
                uv.setPerfil(vigilanteRepository.findById(req.getVigilanteId())
                        .orElseThrow(() -> new NotFoundException("Vigilante no encontrado")));
            }

        u.setUsername(req.getUsername());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setEnabled(true);

        Usuario guardado = usuarioRepository.save(u);
        return toResponse(guardado);
    }


    public UsuarioResponse actualizar(Long id, UsuarioUpdateRequest req) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + id));

        // Password opcional
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            u.setPassword(passwordEncoder.encode(req.getPassword()));
        }

        // Enabled opcional
        if (req.getEnabled() != null) {
            u.setEnabled(req.getEnabled());
        }

        // Nota: NO cambiamos el "tipo/rol" en update para evitar inconsistencias.
        // Si quisieras permitirlo, lo correcto es: eliminar + crear nuevo del otro tipo,
        // o manejarlo con una estrategia explícita.

        Usuario guardado = usuarioRepository.save(u);
        return toResponse(guardado);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new NotFoundException("Usuario no encontrado: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private Usuario crearSegunTipo(String tipo) {
        if (tipo == null) {
            throw new BadRequestException("El campo 'tipo' es obligatorio (ADMIN/INVESTIGADOR/VIGILANTE)");
        }

        return switch (tipo.toUpperCase()) {
            case "ADMIN" -> new UsuarioAdmin();
            case "INVESTIGADOR" -> new UsuarioInvestigador();
            case "VIGILANTE" -> new UsuarioVigilante();
            default -> throw new BadRequestException("Tipo de usuario inválido: " + tipo +
                    ". Valores válidos: ADMIN, INVESTIGADOR, VIGILANTE");
        };
    }

    private UsuarioResponse toResponse(Usuario u) {
        // UsuarioResponse típico: (id, username, rol, enabled)
        return new UsuarioResponse(
                u.getId(),
                u.getUsername(),
                u.getRol(),     // viene del tipo/subclase
                u.isEnabled()
        );
    }
}
