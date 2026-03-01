package com.tp.backend.banco.application;

import com.tp.backend.banco.domain.*;
import com.tp.backend.banco.dto.*;
import com.tp.backend.banco.mapper.BancoMapper;
import com.tp.backend.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BancoService implements BancoUseCase {
    private final BancoPort port;
    private final BancoMapper mapper;
    private final List<BancoValidator<BancoRequest>> validators;

    public BancoService(BancoPort port, BancoMapper mapper, List<BancoValidator<BancoRequest>> validators) {
        this.port = port;
        this.mapper = mapper;
        this.validators = validators;
    }

    @Override @Transactional(readOnly = true)
    public List<BancoResponse> listar() {
        return port.listar().stream().map(mapper::toResponse).toList();
    }

    @Override @Transactional(readOnly = true)
    public BancoResponse obtener(Long id) {
        return port.obtener(id).map(mapper::toResponse).orElseThrow(() -> new NotFoundException("Banco no encontrado"));
    }

    @Override @Transactional
    public BancoResponse crear(BancoRequest req) {
        validators.forEach(v -> v.validar(req));
        Banco b = mapper.toEntity(req);
        return mapper.toResponse(port.guardar(b));
    }

    @Override @Transactional
    public BancoResponse actualizar(Long id, BancoRequest req) {
        Banco b = port.obtener(id).orElseThrow(() -> new NotFoundException("Banco no encontrado"));
        b.setCodigo(req.getCodigo());
        b.setDomicilioCentral(req.getDomicilioCentral());
        return mapper.toResponse(port.guardar(b));
    }

    @Override @Transactional
    public void eliminar(Long id) {
        if (!port.existsById(id)) throw new NotFoundException("Banco no existe");
        port.eliminar(id);
    }
}