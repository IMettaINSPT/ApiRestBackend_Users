package com.tp.backend.service;

//avances nuevos

import com.tp.backend.dto.contrato.ContratoResponse;
import com.tp.backend.dto.vigilante.*;
import com.tp.backend.exception.BadRequestException;
import com.tp.backend.exception.NotFoundException;
import com.tp.backend.model.Vigilante;
import com.tp.backend.repository.VigilanteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VigilanteService {

    private final VigilanteRepository repo;

    public VigilanteService(VigilanteRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<VigilanteResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public VigilanteResponse obtener(Long id) {
        Vigilante v = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Vigilante no encontrado: " + id));
        return toResponse(v);
    }

    @Transactional
    public VigilanteResponse crear(VigilanteRequest req) {
        if (repo.existsByCodigo(req.getCodigo())) {
            throw new BadRequestException("Ya existe un vigilante con código: " + req.getCodigo());
        }

        Vigilante v = new Vigilante();
        v.setCodigo(req.getCodigo());
        v.setEdad(req.getEdad());

        return toResponse(repo.save(v));
    }

    @Transactional
    public VigilanteResponse actualizar(Long id, VigilanteUpdateRequest req) {
        Vigilante v = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Vigilante no encontrado: " + id));

        if (!v.getCodigo().equals(req.getCodigo()) && repo.existsByCodigo(req.getCodigo())) {
            throw new BadRequestException("Ya existe un vigilante con código: " + req.getCodigo());
        }

        v.setCodigo(req.getCodigo());
        v.setEdad(req.getEdad());

        return toResponse(v);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Vigilante no encontrado: " + id);
        }
        repo.deleteById(id);
    }

    // --- MÉTODOS AGREGADOS PARA EL CONTROLLER ---

    @Transactional(readOnly = true)
    public List<VigilanteResponse> listarDisponibles() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public long countDisponibles() {
        return repo.count();
    }

    @Transactional(readOnly = true)
    public VigilanteResponse obtenerMiPerfil(String codigo) {
        Vigilante v = repo.findByCodigo(codigo)
                .orElseThrow(() -> new NotFoundException("Perfil no encontrado: " + codigo));
        return toResponse(v);
    }

    // --------------------------------------------

    private VigilanteResponse toResponse(Vigilante v) {
        VigilanteResponse res = new VigilanteResponse(v.getId(), v.getCodigo(), v.getEdad());

        if (v.getContratos() != null) {
            res.setContratos(v.getContratos().stream()
                    .map(c -> new ContratoResponse(
                            c.getId(),
                            c.getNumContrato(),
                            c.getFechaContrato(),
                            c.isConArma(),
                            c.getSucursal().getId(),
                            c.getSucursal().getCodigo(),
                            v.getId(),
                            v.getCodigo()
                    ))
                    .collect(Collectors.toList()));
        }

        return res;
    }
}