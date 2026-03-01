package com.tp.backend.juez.application;

import com.tp.backend.juez.domain.*;
import com.tp.backend.juez.mapper.JuezMapper;
import com.tp.backend.juez.dto.*;
import com.tp.backend.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class JuezService implements JuezUseCase {
    private final JuezPort port;
    private final JuezMapper mapper;
    private final List<JuezValidator<JuezRequest>> validators;

    public JuezService(JuezPort port, JuezMapper mapper, List<JuezValidator<JuezRequest>> validators) {
        this.port = port;
        this.mapper = mapper;
        this.validators = validators;
    }

    @Override @Transactional(readOnly = true)
    public List<JuezResponse> listar() {
        return port.listar().stream().map(mapper::toResponse).toList();
    }

    @Override @Transactional(readOnly = true)
    public JuezResponse obtener(Long id) {
        return port.obtener(id).map(mapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Juez no encontrado: " + id));
    }

    @Override @Transactional
    public JuezResponse crear(JuezRequest req) {
        validators.forEach(v -> v.validar(req));
        Juez j = mapper.toEntity(req);
        return mapper.toResponse(port.guardar(j));
    }

    @Override @Transactional
    public JuezResponse actualizar(Long id, JuezUpdateRequest req) {
        Juez j = port.obtener(id).orElseThrow(() -> new NotFoundException("Juez no encontrado"));

        // Validación de clave duplicada en actualización (lógica original)
        if (!j.getClaveJuzgado().equals(req.getClaveJuzgado()) && port.existsByClaveJuzgado(req.getClaveJuzgado())) {
            throw new com.tp.backend.exception.BadRequestException("Ya existe un juez con código: " + req.getClaveJuzgado());
        }

        j.setClaveJuzgado(req.getClaveJuzgado());
        j.setNombre(req.getNombre());
        j.setApellido(req.getApellido());
        j.setAnosServicio(req.getAnosServicio());
        return mapper.toResponse(port.guardar(j));
    }

    @Override @Transactional
    public void eliminar(Long id) {
        if (!port.existsById(id)) throw new NotFoundException("Juez no encontrado");
        port.eliminar(id);
    }
}
