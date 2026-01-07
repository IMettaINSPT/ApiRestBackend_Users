package com.tp.backend.service;

import com.tp.backend.dto.juez.*;
import com.tp.backend.exception.BadRequestException;
import com.tp.backend.exception.NotFoundException;
import com.tp.backend.model.Juez;
import com.tp.backend.repository.JuezRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JuezService {

    private final JuezRepository repo;

    public JuezService(JuezRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<JuezResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public JuezResponse obtener(Long id) {
        Juez j = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Juez no encontrado: " + id));
        return toResponse(j);
    }

    @Transactional
    public JuezResponse crear(JuezRequest req) {
        if (repo.existsByCodigo(req.getCodigo())) {
            throw new BadRequestException("Ya existe un juez con código: " + req.getCodigo());
        }

        Juez j = new Juez();
        j.setCodigo(req.getCodigo());
        j.setNombre(req.getNombre());
        j.setApellido(req.getApellido());

        return toResponse(repo.save(j));
    }

    @Transactional
    public JuezResponse actualizar(Long id, JuezUpdateRequest req) {
        Juez j = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Juez no encontrado: " + id));

        if (!j.getCodigo().equals(req.getCodigo()) && repo.existsByCodigo(req.getCodigo())) {
            throw new BadRequestException("Ya existe un juez con código: " + req.getCodigo());
        }

        j.setCodigo(req.getCodigo());
        j.setNombre(req.getNombre());
        j.setApellido(req.getApellido());

        return toResponse(j);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Juez no encontrado: " + id);
        }
        repo.deleteById(id);
    }

    private JuezResponse toResponse(Juez j) {
        return new JuezResponse(j.getId(), j.getCodigo(), j.getNombre(), j.getApellido());
    }
}
