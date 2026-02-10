package com.tp.backend.service;

import com.tp.backend.dto.sucursal.*;
import com.tp.backend.model.Banco;
import com.tp.backend.exception.*;
import com.tp.backend.model.Sucursal;
import com.tp.backend.repository.BancoRepository;
import com.tp.backend.repository.ContratoRepository;
import com.tp.backend.repository.SucursalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class SucursalService {

    private final SucursalRepository sucursalRepo;
    private final BancoRepository bancoRepo;
    private final ContratoRepository contratoRepo;

    public SucursalService(SucursalRepository sucursalRepo, BancoRepository bancoRepo, ContratoRepository contratoRepo) {
        this.sucursalRepo = sucursalRepo;
        this.bancoRepo = bancoRepo;
        this.contratoRepo = contratoRepo;
    }

    @Transactional(readOnly = true)
    public List<SucursalResponse> listar() {
        return sucursalRepo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SucursalResponse obtenerPorId(Long id) {
        Sucursal s = sucursalRepo.findById(id)
                .orElseThrow(() -> new BadRequestException("Sucursal no encontrada: " + id));
        return toResponse(s);
    }

    @Transactional
    public SucursalResponse crear(SucursalRequest req) {
        if (sucursalRepo.existsByCodigo(req.getCodigo())) {
            throw new BadRequestException("Ya existe una sucursal con código: " + req.getCodigo());
        }

        Banco banco = bancoRepo.findById(req.getBancoId())
                .orElseThrow(() -> new NotFoundException("Banco no encontrado: " + req.getBancoId()));

        Sucursal s = new Sucursal();
        s.setCodigo(req.getCodigo());
        s.setDomicilio(req.getDomicilio());
        s.setNroEmpleados(req.getNroEmpleados());
        s.setBanco(banco);

        Sucursal guardada = sucursalRepo.save(s);
        return toResponse(guardada);
    }

    @Transactional
    public SucursalResponse actualizar(Long id, SucursalUpdateRequest req) {
        Sucursal s = sucursalRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Sucursal no encontrada: " + id));

        if (!s.getCodigo().equals(req.getCodigo()) && sucursalRepo.existsByCodigo(req.getCodigo())) {
            throw new BadRequestException("Ya existe una sucursal con código: " + req.getCodigo());
        }

        Banco banco = bancoRepo.findById(req.getBancoId())
                .orElseThrow(() -> new NotFoundException("Banco no encontrado: " + req.getBancoId()));

        s.setCodigo(req.getCodigo());
        s.setDomicilio(req.getDomicilio());
        s.setNroEmpleados(req.getNroEmpleados());
        s.setBanco(banco);

        return toResponse(s);
    }

    @Transactional
    public void eliminar(Long id) {
        Sucursal s = sucursalRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Sucursal no encontrada: " + id));

        boolean tieneActivos = contratoRepo.existsActivoBySucursal(id, LocalDate.now());
        if (tieneActivos) {
            long cant = contratoRepo.countActivosBySucursal(id, LocalDate.now());
            throw new BadRequestException(
                    "No se puede eliminar la sucursal. Tiene " + cant + " contrato(s) activo(s)."
            );
        }

        sucursalRepo.delete(s);
    }

    private SucursalResponse toResponse(Sucursal s) {
        return new SucursalResponse(
                s.getId(),
                s.getCodigo(),
                s.getDomicilio(),
                s.getNroEmpleados(),
                s.getBanco().getId(),
                s.getBanco().getCodigo()
        );
    }
}
