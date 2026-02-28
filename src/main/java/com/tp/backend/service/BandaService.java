package com.tp.backend.service;

import com.tp.backend.dto.banda.*;
import com.tp.backend.dto.personaDetenida.PersonaDetenidaResponse;
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

    // ---------- CONSULTAS ----------

    @Transactional(readOnly = true)
    public List<BandaResponse> listar() {
        return repo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BandaResponse obtener(Long id) {
        Banda b = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Banda no encontrada: " + id));
        return toResponse(b);
    }

    // ---------- CRUD ----------

    @Transactional
    public BandaResponse crear(BandaRequest req) {
        if (repo.existsByNumeroBanda(req.getNumeroBanda())) {
            throw new BadRequestException("Ya existe una banda con este número: " + req.getNumeroBanda());
        }
        Banda b = new Banda();
        b.setNumeroBanda(req.getNumeroBanda());
        b.setNumeroMiembros(req.getNumeroMiembros());
        b = repo.save(b);
        return new BandaResponse(b.getId(), b.getNumeroBanda(), b.getNumeroMiembros());
    }

    @Transactional
    public BandaResponse actualizar(Long id, BandaUpdateRequest req) {
        Banda b = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Banda no encontrada: " + id));

        if (repo.existsByNumeroBanda(req.getNumeroBanda()) && !b.getNumeroBanda().equals(req.getNumeroBanda())) {
            throw new BadRequestException("Ya existe una banda con el nro: " + req.getNumeroBanda());
        }

        b.setNumeroBanda(req.getNumeroBanda());
        b.setNumeroMiembros(req.getNumeroMiembros());
        repo.save(b);

        return new BandaResponse(b.getId(), b.getNumeroBanda(), b.getNumeroMiembros());
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Banda no encontrada: " + id);
        repo.deleteById(id);
    }

    // ---------- HELPER DE MAPEO (Aquí estaba el error de llaves) ----------

    private BandaResponse toResponse(Banda b) {
        BandaResponse res = new BandaResponse(b.getId(), b.getNumeroBanda(), b.getNumeroMiembros());

        if (b.getPersonasDetenidas() != null) {
            // CORRECCIÓN: Los 6 parámetros del Record PersonaDetenidaResponse
            List<PersonaDetenidaResponse> integrantes = b.getPersonasDetenidas().stream()
                    .map(p -> new PersonaDetenidaResponse(
                            p.getId(),
                            p.getCodigo(),
                            p.getNombre(),
                            p.getApellido(),
                            null, // banda (5to)
                            null  // asaltos (6to)
                    ))
                    .toList();
            res.setPersonasDetenidas(integrantes);
        }
        return res;
    }
}