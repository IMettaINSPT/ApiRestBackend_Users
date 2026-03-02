package com.tp.backend.vigilante.application;

import com.tp.backend.vigilante.domain.*;
import com.tp.backend.vigilante.mapper.VigilanteMapper;
import com.tp.backend.vigilante.dto.*;
import com.tp.backend.exception.NotFoundException;
import com.tp.backend.model.UsuarioVigilante;
import com.tp.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class VigilanteService implements VigilanteUseCase {
    private final VigilantePort port;
    private final VigilanteMapper mapper;
    private final List<VigilanteValidator<VigilanteRequest>> createValidators;
    private final UsuarioRepository usuarioRepository;

    public VigilanteService(VigilantePort port, VigilanteMapper mapper,
                            List<VigilanteValidator<VigilanteRequest>> createValidators,
                            UsuarioRepository usuarioRepository) {
        this.port = port;
        this.mapper = mapper;
        this.createValidators = createValidators;
        this.usuarioRepository = usuarioRepository;
    }

    @Override @Transactional(readOnly = true)
    public List<VigilanteResponse> listar() {
        return port.listar().stream().map(mapper::toResponse).toList();
    }

    @Override @Transactional(readOnly = true)
    public VigilanteResponse obtener(Long id) {
        return port.obtener(id).map(mapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Vigilante no encontrado: " + id));
    }

    @Override @Transactional
    public VigilanteResponse crear(VigilanteRequest req) {
        for (VigilanteValidator<VigilanteRequest> v : createValidators) v.validar(req);
        Vigilante v = mapper.toEntity(req);
        return mapper.toResponse(port.guardar(v));
    }

    @Override @Transactional
    public VigilanteResponse actualizar(Long id, VigilanteUpdateRequest req) {
        Vigilante v = port.obtener(id).orElseThrow(() -> new NotFoundException("Vigilante no encontrado"));
        // Lógica de validación de código duplicado para actualización [cite: 291]
        if (!v.getCodigo().equals(req.getCodigo()) && port.existsByCodigo(req.getCodigo())) {
            throw new com.tp.backend.exception.BadRequestException("Ya existe un vigilante con código: " + req.getCodigo());
        }
        v.setCodigo(req.getCodigo());
        v.setEdad(req.getEdad());
        return mapper.toResponse(port.guardar(v));
    }

    @Override @Transactional
    public void eliminar(Long id) {
        if (!port.existsById(id)) throw new NotFoundException("Vigilante no encontrado");
        port.eliminar(id);
    }

    @Override @Transactional(readOnly = true)
    public List<VigilanteResponse> listarDisponibles() {
        return port.findDisponibles().stream().map(mapper::toResponse).toList();
    }

    @Override @Transactional(readOnly = true)
    public long countDisponibles() {
        return port.findDisponibles().size();
    }

    @Override @Transactional(readOnly = true)
    public VigilanteResponse obtenerMiPerfil(String username) {
        var usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + username));
        if (usuario instanceof UsuarioVigilante uv && uv.getPerfil() != null) {
            return mapper.toResponse(uv.getPerfil());
        }
        throw new NotFoundException("Perfil de vigilante no encontrado para el usuario: " + username);
    }
}