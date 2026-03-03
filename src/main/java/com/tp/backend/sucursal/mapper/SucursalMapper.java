package com.tp.backend.sucursal.mapper;

import com.tp.backend.sucursal.domain.Sucursal;
import com.tp.backend.sucursal.dto.*;
import com.tp.backend.banco.domain.Banco;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {
    public SucursalResponse toResponse(Sucursal s) {
        return new SucursalResponse(
                s.getId(),
                s.getCodigo(),
                s.getDomicilio(),
                s.getNroEmpleados(),
                s.getBanco().getId(),
                s.getBanco().getCodigo()
        );
    }

    public Sucursal toEntity(SucursalRequest req, Banco banco) {
        Sucursal s = new Sucursal();
        s.setCodigo(req.getCodigo());
        s.setDomicilio(req.getDomicilio());
        s.setNroEmpleados(req.getNroEmpleados());
        s.setBanco(banco);
        return s;
    }
}