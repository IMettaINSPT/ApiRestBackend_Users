package com.tp.backend.service;

import com.tp.backend.dto.contrato.*;
import com.tp.backend.exception.BadRequestException;
import com.tp.backend.exception.NotFoundException;
import com.tp.backend.model.*;
import com.tp.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepo;
    private final SucursalRepository sucursalRepo;
    private final VigilanteRepository vigilanteRepo;

    public ContratoService(ContratoRepository contratoRepo,
                           SucursalRepository sucursalRepo,
                           VigilanteRepository vigilanteRepo) {
        this.contratoRepo = contratoRepo;
        this.sucursalRepo = sucursalRepo;
        this.vigilanteRepo = vigilanteRepo;
    }

    @Transactional(readOnly = true)
    public List<ContratoResponse> listar() {
        return contratoRepo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ContratoResponse obtener(Long id) {
        Contrato c = contratoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Contrato no encontrado: " + id));
        return toResponse(c);
    }

    @Transactional
    public ContratoResponse crear(ContratoRequest req) {

        if (contratoRepo.existsByVigilanteIdAndSucursalIdAndFechaContrato(
                req.getVigilanteId(),
                req.getSucursalId(),
                req.getFechaContrato())) {

            throw new BadRequestException(
                    "Ya existe un contrato para ese vigilante, sucursal y fecha"
            );
        }

        Sucursal s = sucursalRepo.findById(req.getSucursalId())
                .orElseThrow(() -> new NotFoundException(
                        "Sucursal no encontrada: " + req.getSucursalId()));

        Vigilante v = vigilanteRepo.findById(req.getVigilanteId())
                .orElseThrow(() -> new NotFoundException(
                        "Vigilante no encontrado: " + req.getVigilanteId()));
        validarRango(req.getFechaContrato(), req.getFechaFin());

        Contrato c = new Contrato();
        c.setFechaContrato(req.getFechaContrato());
        c.setConArma(req.isConArma());
        c.setSucursal(s);
        c.setVigilante(v);

        return toResponse(contratoRepo.save(c));
    }


    @Transactional
    public ContratoResponse actualizar(Long id, ContratoUpdateRequest req) {
        Contrato c = contratoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Contrato no encontrado: " + id));

        Sucursal s = sucursalRepo.findById(req.getSucursalId())
                .orElseThrow(() -> new NotFoundException("Sucursal no encontrada: " + req.getSucursalId()));

        Vigilante v = vigilanteRepo.findById(req.getVigilanteId())
                .orElseThrow(() -> new NotFoundException("Vigilante no encontrado: " + req.getVigilanteId()));

        validarRango(req.getFechaContrato(), req.getFechaFin());
        c.setFechaContrato(req.getFechaContrato());
        c.setConArma(req.isConArma());
        c.setSucursal(s);
        c.setVigilante(v);

        return toResponse(c);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!contratoRepo.existsById(id)) {
            throw new NotFoundException("Contrato no encontrado: " + id);
        }

        contratoRepo.deleteById(id);
    }

    private ContratoResponse toResponse(Contrato c) {
        return new ContratoResponse(
                c.getId(),
                c.getFechaContrato(),
                c.isConArma(),
                c.getSucursal().getId(),
                c.getSucursal().getCodigo(),
                c.getVigilante().getId(),
                c.getVigilante().getCodigo(),
                c.getFechaFin()
        );
    }
    @Transactional(readOnly = true)
    public List<ContratoResponse> listarPorSucursal(Long sucursalId) {
        if (!sucursalRepo.existsById(sucursalId)) {
            throw new NotFoundException("Sucursal no encontrada: " + sucursalId);
        }

        return contratoRepo.findBySucursalId(sucursalId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ContratoResponse> listarPorVigilante(Long vigilanteId) {
        if (!vigilanteRepo.existsById(vigilanteId)) {
            throw new NotFoundException("Vigilante no encontrado: " + vigilanteId);
        }

        return contratoRepo.findByVigilanteId(vigilanteId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private void validarRango(LocalDate inicio, LocalDate fin) {
        if (fin != null && fin.isBefore(inicio)) {
            throw new BadRequestException("La fecha fin no puede ser anterior a la fecha inicio.");
        }
    }

    /*private void validarSolapamiento(Long vigilanteId, LocalDate inicio, LocalDate fin, Long excludeId) {
        if (contratoRepo.existsSolapadoVigilante(vigilanteId, inicio, fin, excludeId)) {
            throw new BadRequestException("El vigilante ya tiene un contrato activo/solapado en ese período.");
        }
    }*/
}
