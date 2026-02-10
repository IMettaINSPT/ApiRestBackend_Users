package com.tp.backend.service;

import com.tp.backend.dto.banco.*;
import com.tp.backend.model.Banco;
import com.tp.backend.exception.*;
import com.tp.backend.repository.BancoRepository;
import com.tp.backend.repository.SucursalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BancoService {

    private final BancoRepository repo;
    private final SucursalRepository sucursalRepository;

    public BancoService(BancoRepository repo, SucursalRepository sucursalRepository) {
        this.repo = repo;
        this.sucursalRepository = sucursalRepository;
    }

    @Transactional(readOnly = true)
    public List<BancoResponse> listar() {
        return repo.findAll().stream()
                .map(b -> new BancoResponse(b.getId(), b.getCodigo(), b.getDomicilioCentral()))
                .toList();
    }

    @Transactional(readOnly = true)
    public BancoResponse obtenerPorId(Long id) {
        Banco b = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Banco no encontrado: " + id));
        return new BancoResponse(b.getId(), b.getCodigo(), b.getDomicilioCentral());
    }

    @Transactional
    public BancoResponse crear(BancoRequest req) {
        if (repo.existsByCodigo(req.getCodigo())) {
            throw new BadRequestException("Ya existe un banco con código: " + req.getCodigo());
        }

        Banco b = new Banco();
        b.setCodigo(req.getCodigo());
        b.setDomicilioCentral(req.getDomicilioCentral());

        Banco guardado = repo.save(b);
        return new BancoResponse(guardado.getId(), guardado.getCodigo(), guardado.getDomicilioCentral());
    }

    @Transactional
    public BancoResponse actualizar(Long id, BancoUpdateRequest req) {
        Banco b = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Banco no encontrado: " + id));

        // si cambia código, validar duplicado
        if (!b.getCodigo().equals(req.getCodigo()) && repo.existsByCodigo(req.getCodigo())) {
            throw new BadRequestException("Ya existe un banco con código: " + req.getCodigo());
        }

        b.setCodigo(req.getCodigo());
        b.setDomicilioCentral(req.getDomicilioCentral());

        return new BancoResponse(b.getId(), b.getCodigo(), b.getDomicilioCentral());
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Banco no encontrado: " + id);
        }

        if (repo.existsById(id)) {
            long cant = sucursalRepository.countByBanco_id(id);
            throw new BadRequestException("No es posible borrar el banco. Tiene " + cant + " sucursal(es) asociada(s).");
        }

        repo.deleteById(id);
    }
}
