package com.tp.backend.service;

import com.tp.backend.dto.usuario.UsuarioRequest;
import com.tp.backend.dto.usuario.UsuarioResponse;
import com.tp.backend.dto.usuario.UsuarioUpdateRequest;
import com.tp.backend.exception.BadRequestException;
import com.tp.backend.exception.NotFoundException;
import com.tp.backend.model.*;
import com.tp.backend.repository.UsuarioRepository;
import com.tp.backend.repository.UsuarioVigilanteRepository;
import com.tp.backend.repository.VigilanteRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioVigilanteRepository usuarioVigilanteRepository;
    private final PasswordEncoder passwordEncoder;
    private final VigilanteRepository vigilanteRepository;
    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    public UsuarioService(UsuarioRepository usuarioRepository,
                          UsuarioVigilanteRepository usuarioVigilanteRepository,
                          PasswordEncoder passwordEncoder,
                          VigilanteRepository vigilanteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioVigilanteRepository = usuarioVigilanteRepository;
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

        log.info("Creando usuario username={} tipo={}", req.getUsername(), req.getRol());

        if (usuarioRepository.existsByUsername(req.getUsername())) {
            log.warn("Intento de crear usuario duplicado username={}", req.getUsername());
            throw new BadRequestException("Ya existe un usuario con username: " + req.getUsername());
        }

        // Crear según tipo (ADMIN / INVESTIGADOR / VIGILANTE)
        Usuario u = crearSegunTipo(req.getRol());

        // Si NO es vigilante, no aceptamos vigilanteId (para evitar confusiones)
        if (!(u instanceof UsuarioVigilante) && req.getVigilanteId() != null) {
            log.warn("Intento de crear un usuario vigilante con otro Tipo");
            throw new BadRequestException("vigilanteId solo aplica cuando el tipo es VIGILANTE");
        }

        // Si es vigilante: validar vigilanteId y que no esté ya asociado
        if (u instanceof UsuarioVigilante uv) {

            Long vigilanteId = req.getVigilanteId();
            if (vigilanteId == null) {
                log.error("Intento de crear usuario vigilante sin perfil asociado");
                throw new BadRequestException("Para VIGILANTE se requiere vigilanteId");
            }

            if (usuarioVigilanteRepository.existsByPerfil_Id(vigilanteId)) {
                log.error("El vigilante ya está asociado a otro usuario idVigilante{}", vigilanteId);
                throw new BadRequestException("El vigilante ya está asociado a otro usuario");
            }

            Vigilante vig = vigilanteRepository.findById(vigilanteId)
                    .orElseThrow(() -> new NotFoundException("Vigilante no encontrado: " + vigilanteId));

            uv.setPerfil(vig);
        }


        u.setCodigo(req.getCodigo());
        u.setUsername(req.getUsername());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setEnabled(true);

        Usuario guardado = usuarioRepository.save(u);
        return toResponse(guardado);
    }

    public UsuarioResponse actualizar(Long id, UsuarioUpdateRequest req) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + id));


        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            u.setPassword(passwordEncoder.encode(req.getPassword()));
        }


        if (req.getEnabled() != null) {
            u.setEnabled(req.getEnabled());
        }


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
        Long vigilanteId = null;
        String vigilanteCodigo = null;

        // CAMBIO: Se realiza un acceso seguro al perfil para evitar LazyInitializationException
        // y asegurar que si el usuario no es Vigilante (como un Admin o Investigador), los campos sean null.
        if (u instanceof UsuarioVigilante uv) {
            Vigilante p = uv.getPerfil();
            if (p != null) {
                vigilanteId = p.getId();
                vigilanteCodigo = p.getCodigo();
            }
        }

        return new UsuarioResponse(
                u.getId(),
                u.getCodigo(),
                u.getUsername(),
                u.getRol(),
                u.isEnabled(),
                vigilanteId,
                vigilanteCodigo
        );
    }

    @Transactional(readOnly = true)
    public UsuarioResponse obtenerPerfilAutenticado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario u = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + username));

        return toResponse(u);
    }
}