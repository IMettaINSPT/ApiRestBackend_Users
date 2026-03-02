package com.tp.backend.sucursal.application;

import com.tp.backend.sucursal.domain.*;
import com.tp.backend.sucursal.dto.*;
import com.tp.backend.sucursal.mapper.SucursalMapper;
import com.tp.backend.banco.domain.BancoPort;
import com.tp.backend.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SucursalService implements SucursalUseCase {
    private final SucursalPort port;
    private final BancoPort bancoPort;
    private final SucursalMapper mapper;
    private final List<SucursalValidator<SucursalRequest>> validators;

    public SucursalService(SucursalPort port, BancoPort bancoPort, SucursalMapper mapper, List<SucursalValidator<SucursalRequest>> validators) {
        this.port = port;
        this.bancoPort = bancoPort;
        this.mapper = mapper;
        this.validators = validators;
    }

    @Override @Transactional(readOnly = true)
    public List<SucursalResponse> listar() {
        return port.listar().stream().map(mapper::toResponse).toList();
    }

    @Override @Transactional(readOnly = true)
    public SucursalResponse obtener(Long id) {
        return port.obtener(id).map(mapper::toResponse).orElseThrow(() -> new NotFoundException("Sucursal no encontrada"));
    }

    @Override @Transactional
    public SucursalResponse crear(SucursalRequest req) {
        validators.forEach(v -> v.validar(req));
        var banco = bancoPort.obtener(req.getBancoId()).orElseThrow(() -> new NotFoundException("Banco no encontrado"));
        Sucursal s = mapper.toEntity(req, banco);
        return mapper.toResponse(port.guardar(s));
    }

    @Override @Transactional
    public SucursalResponse actualizar(Long id, SucursalRequest req) {
        Sucursal s = port.obtener(id).orElseThrow(() -> new NotFoundException("Sucursal no encontrada"));
        var banco = bancoPort.obtener(req.getBancoId()).orElseThrow(() -> new NotFoundException("Banco no encontrado"));

        s.setCodigo(req.getCodigo());
        s.setDomicilio(req.getDomicilio());
        s.setNumEmpleados(req.getNroEmpleados());
        s.setBanco(banco);

        return mapper.toResponse(port.guardar(s));
    }

    @Override @Transactional
    public void eliminar(Long id) {
        if (!port.existsById(id)) throw new NotFoundException("Sucursal no existe");
        port.eliminar(id);
    }

    @Override @Transactional(readOnly = true)
    public List<SucursalResponse> listarPorBanco(Long bancoId) {
        return port.findByBancoId(bancoId).stream().map(mapper::toResponse).toList();
    }
}