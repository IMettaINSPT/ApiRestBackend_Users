package com.tp.backend.service;

import com.tp.backend.dto.banda.*;
import com.tp.backend.exception.BadRequestException;
import com.tp.backend.exception.NotFoundException;
import com.tp.backend.model.Banda;
import com.tp.backend.repository.BandaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BandaService {

    private final BandaRepository repo;

    public BandaService(BandaRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<BandaResponse> listar() {
        return repo.findAll().stream().map(b -> new BandaResponse(b.getId(), b.getNombre())).toList();
    }

    @Transactional(readOnly = true)
    public BandaResponse obtener(Long id) {
        Banda b = repo.findById(id).orElseThrow(() -> new NotFoundException("Banda no encontrada: " + id));
        return new BandaResponse(b.getId(), b.getNombre());
    }

    @Transactional
    public BandaResponse crear(BandaRequest req) {
        if (repo.existsByNombre(req.getNombre())) throw new BadRequestException("Ya existe una banda con nombre: " + req.getNombre());
        Banda b = new Banda();
        b.setNombre(req.getNombre());
        b = repo.save(b);
        return new BandaResponse(b.getId(), b.getNombre());
    }

    @Transactional
    public BandaResponse actualizar(Long id, BandaUpdateRequest req) {
        Banda b = repo.findById(id).orElseThrow(() -> new NotFoundException("Banda no encontrada: " + id));
        if (!b.getNombre().equals(req.getNombre()) && repo.existsByNombre(req.getNombre())) {
            throw new BadRequestException("Ya existe una banda con nombre: " + req.getNombre());
        }
        b.setNombre(req.getNombre());
        return new BandaResponse(b.getId(), b.getNombre());
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Banda no encontrada: " + id);
        repo.deleteById(id);
    }
}
