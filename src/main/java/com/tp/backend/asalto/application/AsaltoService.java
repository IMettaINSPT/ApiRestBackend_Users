package com.tp.backend.asalto.application;

import com.tp.backend.asalto.domain.*;
import com.tp.backend.asalto.mapper.AsaltoMapper;
import com.tp.backend.asalto.dto.*;
import com.tp.backend.personaDetenida.dto.PersonaDetenidaResponse;
import com.tp.backend.exception.NotFoundException;
import com.tp.backend.exception.BadRequestException;
import com.tp.backend.sucursal.domain.Sucursal;
import com.tp.backend.sucursal.infrastructure.SucursalRepository;
import com.tp.backend.personaDetenida.domain.PersonaDetenida;
import com.tp.backend.personaDetenida.infrastructure.PersonaDetenidaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class AsaltoService implements AsaltoUseCase {
    private final AsaltoPort port;
    private final AsaltoMapper mapper;
    private final List<AsaltoValidator<AsaltoRequest>> validators;
    private final SucursalRepository sucursalRepo;
    private final PersonaDetenidaRepository personaRepo;

    public AsaltoService(AsaltoPort port, AsaltoMapper mapper,
                         List<AsaltoValidator<AsaltoRequest>> validators,
                         SucursalRepository sucursalRepo, PersonaDetenidaRepository personaRepo) {
        this.port = port;
        this.mapper = mapper;
        this.validators = validators;
        this.sucursalRepo = sucursalRepo;
        this.personaRepo = personaRepo;
    }

    @Override @Transactional(readOnly = true)
    public List<AsaltoResponse> listarConFiltros(Long sucursalId, LocalDate fecha, LocalDate desde, LocalDate hasta) {
        return port.filtrar(sucursalId, fecha, desde, hasta).stream().map(mapper::toResponse).toList();
    }

    @Override @Transactional(readOnly = true)
    public AsaltoResponse buscarPorId(Long id) {
        return port.buscarPorId(id).map(mapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Asalto no encontrado: " + id));
    }

    @Override @Transactional
    public AsaltoResponse crear(AsaltoRequest req) {
        for (AsaltoValidator<AsaltoRequest> v : validators) v.validar(req);
        Sucursal s = sucursalRepo.findById(req.getSucursalId()).orElseThrow(() -> new NotFoundException("Sucursal no encontrada"));
        List<PersonaDetenida> personas = personaRepo.findAllById(req.getPersonaDetenidaIds());
        if (personas.isEmpty()) throw new BadRequestException("Debe seleccionar al menos una persona válida");

        Asalto a = mapper.toEntity(req, s, personas);
        return mapper.toResponse(port.guardar(a));
    }

    @Override @Transactional
    public AsaltoResponse actualizar(Long id, AsaltoRequest req) {
        for (AsaltoValidator<AsaltoRequest> v : validators) v.validar(req);
        Asalto a = port.buscarPorId(id).orElseThrow(() -> new NotFoundException("Asalto no encontrado"));
        Sucursal s = sucursalRepo.findById(req.getSucursalId()).orElseThrow(() -> new NotFoundException("Sucursal no encontrada"));
        List<PersonaDetenida> personas = personaRepo.findAllById(req.getPersonaDetenidaIds());

        a.setCodigo(req.getCodigo());
        a.setFechaAsalto(req.getFechaAsalto());
        a.setSucursal(s);
        a.setPersonas(personas);
        return mapper.toResponse(port.guardar(a));
    }

    @Override @Transactional
    public void eliminar(Long id) {
        if (!port.existsById(id)) throw new NotFoundException("Asalto no encontrado");
        port.eliminar(id);
    }

    @Override @Transactional(readOnly = true)
    public List<PersonaDetenidaResponse> listarPersonasPorAsalto(Long id) {
        return buscarPorId(id).getPersonas();
    }
}