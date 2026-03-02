package com.tp.backend.usuario.application;

import com.tp.backend.usuario.domain.*;
import com.tp.backend.usuario.dto.*;
import com.tp.backend.usuario.mapper.UsuarioMapper;
import com.tp.backend.exception.*;
import com.tp.backend.vigilante.domain.VigilantePort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UsuarioService implements UsuarioUseCase {
    private final UsuarioPort port;
    private final VigilantePort vigilantePort;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioPort port, VigilantePort vigilantePort, UsuarioMapper mapper, PasswordEncoder passwordEncoder) {
        this.port = port;
        this.vigilantePort = vigilantePort;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        return port.listar().stream().map(mapper::toResponse).toList();
    }

    @Override @Transactional(readOnly = true)
    public UsuarioResponse obtener(Long id) {
        return port.obtenerPorId(id).map(mapper::toResponse).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    }

    @Override @Transactional
    public UsuarioResponse crear(UsuarioRequest req) {
        if (port.existsByUsername(req.getUsername())) throw new BadRequestException("Username ya existe");

        // 1. Usar el mapper para crear la instancia correcta (Admin, Investigador o Vigilante)
        Usuario u = mapper.toEntity(req);
        u.setPassword(passwordEncoder.encode(req.getPassword()));

        // 2. Lógica para asociar el perfil si es Vigilante
        if (u instanceof com.tp.backend.usuario.domain.UsuarioVigilante uv && req.getVigilanteId() != null) {
            var vig = vigilantePort.obtener(req.getVigilanteId())
                    .orElseThrow(() -> new NotFoundException("Vigilante no encontrado"));
            uv.setPerfil(vig);
        }

        return mapper.toResponse(port.guardar(u));
    }

    @Override @Transactional
    public UsuarioResponse actualizar(Long id, UsuarioUpdateRequest req) {
        Usuario u = port.obtenerPorId(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        if (req.getPassword() != null && !req.getPassword().isBlank()) u.setPassword(passwordEncoder.encode(req.getPassword()));
        if (req.getEnabled() != null) u.setEnabled(req.getEnabled());
        return mapper.toResponse(port.guardar(u));
    }

    @Override @Transactional
    public void eliminar(Long id) {
        if (port.obtenerPorId(id).isEmpty()) throw new NotFoundException("ID no existe");
        port.eliminar(id);
    }

    @Override @Transactional(readOnly = true)
    public UsuarioResponse obtenerPerfilAutenticado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return port.obtenerPorUsername(username).map(mapper::toResponse).orElseThrow(() -> new NotFoundException("Perfil no encontrado"));
    }
}