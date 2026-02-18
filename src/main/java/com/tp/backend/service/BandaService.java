package com.tp.backend.service;

import com.tp.backend.dto.banda.*;
import com.tp.backend.dto.personaDetenida.PersonaDetenidaResponse;
import com.tp.backend.dto.sucursal.SucursalResponse;
import com.tp.backend.exception.BadRequestException;
import com.tp.backend.exception.NotFoundException;
import com.tp.backend.model.Banda;
import com.tp.backend.repository.BandaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BandaService {

    private final BandaRepository repo;

    public BandaService(BandaRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<BandaResponse> listar() {
        return repo.findAll().stream().
                map(b -> {
                    BandaResponse res = new BandaResponse(b.getId(), b.getNumeroBanda(), b.getNumeroMiembros());
                    if (b.getPersonasDetenidas() != null) {
                        res.setPersonasDetenidas(b.getPersonasDetenidas().stream()
                                .map(p -> new PersonaDetenidaResponse(
                                        p.getId(),
                                        p.getCodigo(),
                                        p.getNombre(),
                                        p.getApellido(),
                                        null
                                ))
                                .collect(Collectors.toList()));
                    }
                    return res;

                })
                .toList();


    }

    @Transactional(readOnly = true)
    public BandaResponse obtener(Long id) {
        Banda b = repo.findById(id).orElseThrow(() -> new NotFoundException("Banda no encontrada: " + id));

        BandaResponse res = new BandaResponse(b.getId(), b.getNumeroBanda(), b.getNumeroMiembros());
        if (b.getPersonasDetenidas() != null) {
            res.setPersonasDetenidas(b.getPersonasDetenidas().stream()
                    .map(p -> new PersonaDetenidaResponse(
                            p.getId(),
                            p.getCodigo(),
                            p.getNombre(),
                            p.getApellido(),
                            null
                    ))
                    .collect(Collectors.toList()));
        }
        return res;

    }

    @Transactional
    public BandaResponse crear(BandaRequest req) {
        if (repo.existsByNumeroBanda(req.getNumeroBanda())) throw new BadRequestException("Ya existe una banda con este numero: " + req.getNumeroBanda());
        Banda b = new Banda();
        b.setNumeroBanda(req.getNumeroBanda());
        b.setNumeroMiembros(req.getNumeroMiembros());
        b = repo.save(b);
        return new BandaResponse(b.getId(), b.getNumeroBanda(), b.getNumeroMiembros());
    }

    @Transactional
    public BandaResponse actualizar(Long id, BandaUpdateRequest req) {
        Banda b = repo.findById(id).orElseThrow(() -> new NotFoundException("Banda no encontrada: " + id));


        b.setNumeroBanda(req.getNumeroBanda());
        b.setNumeroMiembros(req.getNumeroMiembros());


        if (
                repo.existsByNumeroBanda(req.getNumeroBanda()) &&
                        !b.getNumeroBanda().equals(req.getNumeroBanda())
        ) {
            throw new BadRequestException("Ya existe una banda con el nro: " + req.getNumeroBanda());
        }
        repo.save(b);
        return new BandaResponse(b.getId(), b.getNumeroBanda(), b.getNumeroMiembros());

    }

    @Transactional
    public void eliminar(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Banda no encontrada: " + id);
        repo.deleteById(id);
    }
}
