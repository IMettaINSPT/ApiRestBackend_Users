package com.tp.backend.contrato.application;

import com.tp.backend.contrato.domain.Contrato;
import com.tp.backend.contrato.domain.ContratoPort;
import com.tp.backend.contrato.domain.ContratoValidator;
import com.tp.backend.contrato.dto.ContratoRequest;
import com.tp.backend.contrato.dto.ContratoResponse;
import com.tp.backend.contrato.dto.ContratoUpdateRequest;
import com.tp.backend.contrato.mapper.ContratoMapper;
import com.tp.backend.exception.NotFoundException;
import com.tp.backend.sucursal.domain.Sucursal;
import com.tp.backend.sucursal.infrastructure.SucursalRepository;
import com.tp.backend.vigilante.infrastructure.VigilanteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContratoService implements ContratoUseCase {
    private final ContratoPort port;
    private final ContratoMapper mapper;
    private final List<ContratoValidator<ContratoRequest>> validators;
    private final SucursalRepository sucursalRepo;
    private final VigilanteRepository vigilanteRepo;

    public ContratoService(ContratoPort port, ContratoMapper mapper, List<ContratoValidator<ContratoRequest>> validators,
                           SucursalRepository sucursalRepo, VigilanteRepository vigilanteRepo) {
        this.port = port;
        this.mapper = mapper;
        this.validators = validators;
        this.sucursalRepo = sucursalRepo;
        this.vigilanteRepo = vigilanteRepo;
    }

    @Override @Transactional(readOnly = true)
    public List<ContratoResponse> listar() {
        return port.listar().stream().map(mapper::toResponse).toList();
    }

    @Override @Transactional(readOnly = true)
    public ContratoResponse obtener(Long id) {
        return port.obtener(id).map(mapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Contrato no encontrado: " + id));
    }

    @Override @Transactional
    public ContratoResponse crear(ContratoRequest req) {
        for (ContratoValidator<ContratoRequest> v : validators) {
            v.validar(req);
        }

        Sucursal s = sucursalRepo.findById(req.getSucursalId()).orElseThrow(() -> new NotFoundException("Sucursal no encontrada"));
        var v = vigilanteRepo.findById(req.getVigilanteId()).orElseThrow(() -> new NotFoundException("Vigilante no encontrado"));

        Contrato c = mapper.toEntity(req, s, v);
        c.setNumContrato("CON-" + (int)(Math.random() * 1000000));
        return mapper.toResponse(port.guardar(c));
    }

    @Override @Transactional
    public ContratoResponse actualizar(Long id, ContratoUpdateRequest req) {
        Contrato c = port.obtener(id).orElseThrow(() -> new NotFoundException("Contrato no encontrado"));
        var s = sucursalRepo.findById(req.getSucursalId()).orElseThrow(() -> new NotFoundException("Sucursal no encontrada"));
        var v = vigilanteRepo.findById(req.getVigilanteId()).orElseThrow(() -> new NotFoundException("Vigilante no encontrado"));

        c.setFechaContrato(req.getFechaContrato());
        c.setConArma(req.isConArma());
        c.setSucursal(s);
        c.setVigilante(v);
        c.setFechaFin(req.getFechaFin());
        return mapper.toResponse(port.guardar(c));
    }

    @Override @Transactional
    public void eliminar(Long id) {
        if (!port.existsById(id)) throw new NotFoundException("ID no existe");
        port.eliminar(id);
    }

    @Override @Transactional(readOnly = true)
    public List<ContratoResponse> listarPorSucursal(Long id) {
        return port.findBySucursalId(id).stream().map(mapper::toResponse).toList();
    }

    @Override @Transactional(readOnly = true)
    public List<ContratoResponse> listarPorVigilante(Long id) {
        return port.findByVigilanteId(id).stream().map(mapper::toResponse).toList();
    }

}