package com.tp.backend.banco.mapper;

import com.tp.backend.banco.domain.Banco;
import com.tp.backend.banco.dto.*;
import com.tp.backend.sucursal.mapper.SucursalMapper; // Importamos el mapper de sucursal
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public class BancoMapper {

    private final SucursalMapper sucursalMapper;

    // Inyectamos el sucursalMapper para poder convertir los objetos completos
    public BancoMapper(SucursalMapper sucursalMapper) {
        this.sucursalMapper = sucursalMapper;
    }

    public BancoResponse toResponse(Banco b) {
        return new BancoResponse(
                b.getId(),
                b.getCodigo(),
                b.getDomicilioCentral(),
                // CAMBIO: Ahora mapeamos a objetos SucursalResponse, no a Strings
                b.getSucursales() != null
                        ? b.getSucursales().stream().map(sucursalMapper::toResponse).toList()
                        : new ArrayList<>()
        );
    }

    public Banco toEntity(BancoRequest req) {
        Banco b = new Banco();
        b.setCodigo(req.getCodigo());
        b.setDomicilioCentral(req.getDomicilioCentral());
        return b;
    }
}