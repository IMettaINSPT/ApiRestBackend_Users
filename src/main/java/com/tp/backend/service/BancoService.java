package com.tp.backend.service;

import com.tp.backend.dto.banco.*;
import com.tp.backend.dto.sucursal.SucursalResponse; // Asegúrate de que esta ruta sea correcta
import com.tp.backend.model.Banco;
import com.tp.backend.exception.*;
import com.tp.backend.repository.BancoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BancoService {

    private final BancoRepository repo;

    public BancoService(BancoRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<BancoResponse> listar() {
        return repo.findAll().stream()
                .map(b -> {
                    BancoResponse res = new BancoResponse(b.getId(), b.getCodigo(), b.getDomicilioCentral());
                    if (b.getSucursales() != null) {
                        res.setSucursales(b.getSucursales().stream()
                                .map(s -> new SucursalResponse(s.getId(), s.getCodigo(), s.getDomicilio(), s.getNroEmpleados(), b.getId(), b.getCodigo()))
                                .collect(Collectors.toList()));
                    }
                    return res;
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public BancoResponse obtenerPorId(Long id) {
        Banco b = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Banco no encontrado: " + id));

        BancoResponse res = new BancoResponse(b.getId(), b.getCodigo(), b.getDomicilioCentral());
        if (b.getSucursales() != null) {
            res.setSucursales(b.getSucursales().stream()
                    .map(s -> new SucursalResponse(s.getId(), s.getCodigo(), s.getDomicilio(), s.getNroEmpleados(), b.getId(), b.getCodigo()))
                    .collect(Collectors.toList()));
        }
        return res;
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
        repo.deleteById(id);
    }
}