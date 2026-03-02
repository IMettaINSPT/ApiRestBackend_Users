package com.tp.backend.banda.application;

import com.tp.backend.banda.domain.*;
import com.tp.backend.banda.mapper.BandaMapper;
import com.tp.backend.banda.dto.*;
import com.tp.backend.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BandaService implements BandaUseCase {
    private final BandaPort port;
    private final BandaMapper mapper;
    private final List<BandaValidator<BandaRequest>> validators;

    public BandaService(BandaPort port, BandaMapper mapper, List<BandaValidator<BandaRequest>> validators) {
        this.port = port;
        this.mapper = mapper;
        this.validators = validators;
    }

    @Override @Transactional(readOnly = true)
    public List<BandaResponse> listar() {
        return port.listar().stream().map(mapper::toResponse).toList();
    }

    @Override @Transactional(readOnly = true)
    public BandaResponse obtener(Long id) {
        return port.obtener(id).map(mapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Banda no encontrada: " + id));
    }

    @Override @Transactional
    public BandaResponse crear(BandaRequest req) {
        validators.forEach(v -> v.validar(req));
        Banda b = mapper.toEntity(req);
        return mapper.toResponse(port.guardar(b));
    }

    @Override @Transactional
    public BandaResponse actualizar(Long id, BandaUpdateRequest req) {
        Banda b = port.obtener(id).orElseThrow(() -> new NotFoundException("Banda no encontrada"));

        // Validación manual de número duplicado para actualización
        if (port.existsByNumeroBanda(req.getNumeroBanda()) && !b.getNumeroBanda().equals(req.getNumeroBanda())) {
            throw new com.tp.backend.exception.BadRequestException("Número de banda ya existe");
        }

        b.setNumeroBanda(req.getNumeroBanda());
        b.setNumeroMiembros(req.getNumeroMiembros());
        return mapper.toResponse(port.guardar(b));
    }

    @Override @Transactional
    public void eliminar(Long id) {
        if (!port.existsById(id)) throw new NotFoundException("Banda no encontrada");
        port.eliminar(id);
    }
}
