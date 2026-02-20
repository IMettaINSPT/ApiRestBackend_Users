package com.tp.backend.service;

import com.tp.backend.dto.asalto.*;
import com.tp.backend.dto.sucursal.SucursalResponse;
import com.tp.backend.dto.PersonaDetenida.PersonaDetenidaResponse;
import com.tp.backend.exception.BadRequestException;
import com.tp.backend.exception.NotFoundException;
import com.tp.backend.model.Asalto;
import com.tp.backend.model.PersonaDetenida;
import com.tp.backend.model.Sucursal;
import com.tp.backend.repository.AsaltoRepository;
import com.tp.backend.repository.PersonaDetenidaRepository;
import com.tp.backend.repository.SucursalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsaltoService {

    private final AsaltoRepository asaltoRepository;
    private final SucursalRepository sucursalRepository;
    private final PersonaDetenidaRepository personaDetenidaRepository;

    public AsaltoService(AsaltoRepository asaltoRepository,
                         SucursalRepository sucursalRepository,
                         PersonaDetenidaRepository personaDetenidaRepository) {
        this.asaltoRepository = asaltoRepository;
        this.sucursalRepository = sucursalRepository;
        this.personaDetenidaRepository = personaDetenidaRepository;
    }

    // ---------- CONSULTAS ----------
    @Transactional(readOnly = true)
    public List<AsaltoResponse> listarConFiltros(Long sucursalId, LocalDate fecha, LocalDate desde, LocalDate hasta) {

        validarFiltros(fecha, desde, hasta);

        List<Asalto> asaltos;

        if (sucursalId == null && fecha == null && desde == null) {
            asaltos = asaltoRepository.findAll();
        } else if (sucursalId != null && fecha != null) {
            asaltos = asaltoRepository.findBySucursal_IdAndFechaAsalto(sucursalId, fecha);
        } else if (sucursalId != null && desde != null) {
            asaltos = asaltoRepository.findBySucursal_IdAndFechaAsaltoBetween(sucursalId, desde, hasta);
        } else if (sucursalId != null) {
            asaltos = asaltoRepository.findBySucursal_Id(sucursalId);
        } else {
            throw new BadRequestException("Para filtrar por fecha/rango se requiere sucursalId.");
        }

        return asaltos.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<AsaltoResponse> listarPorPersonaDetenida(Long personaDetenidaId) {
        return asaltoRepository.findByPersonaDetenida_Id(personaDetenidaId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ---------- CRUD ----------
    @Transactional(readOnly = true)
    public AsaltoResponse buscarPorId(Long id) {
        Asalto a = asaltoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Asalto no encontrado: " + id));
        return toResponse(a);
    }

    @Transactional
    public AsaltoResponse crear(AsaltoRequest req) {
        validarRequest(req);

        Sucursal sucursal = sucursalRepository.findById(req.getSucursalId())
                .orElseThrow(() -> new NotFoundException("Sucursal no encontrada: " + req.getSucursalId()));

        // Buscamos una LISTA de personas
        List<PersonaDetenida> personas = personaDetenidaRepository.findAllById(req.getPersonaDetenidaIds());
        if (personas.isEmpty()) {
            throw new BadRequestException("Debe seleccionar al menos una persona válida.");
        }

        Asalto a = new Asalto();
        a.setId(req.getId());
        a.setCodigo(req.getCodigo());
        a.setFechaAsalto(req.getFechaAsalto());
        a.setSucursal(sucursal);
        a.setPersonas(personas);

        return toResponse(asaltoRepository.save(a));
    }

    @Transactional
    public AsaltoResponse actualizar(Long id, AsaltoRequest req) {
        validarRequest(req);

        Asalto a = asaltoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Asalto no encontrado: " + id));

        Sucursal sucursal = sucursalRepository.findById(req.getSucursalId())
                .orElseThrow(() -> new NotFoundException("Sucursal no encontrada: " + req.getSucursalId()));

        // Buscamos la nueva LISTA de personas
        List<PersonaDetenida> personas = personaDetenidaRepository.findAllById(req.getPersonaDetenidaIds());
        if (personas.isEmpty()) {
            throw new BadRequestException("Debe seleccionar al menos una persona válida.");
        }

        a.setFechaAsalto(req.getFechaAsalto());
        a.setSucursal(sucursal);
        a.setPersonas(personas);

        return toResponse(asaltoRepository.save(a));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!asaltoRepository.existsById(id)) {
            throw new NotFoundException("Asalto no encontrado: " + id);
        }
        asaltoRepository.deleteById(id);
    }

    // ---------- helpers ----------
    private void validarFiltros(LocalDate fecha, LocalDate desde, LocalDate hasta) {
        if (fecha != null && (desde != null || hasta != null)) {
            throw new BadRequestException("No se puede usar 'fecha' junto con 'desde/hasta'.");
        }
        if ((desde == null) != (hasta == null)) {
            throw new BadRequestException("Si usás rango, enviá 'desde' y 'hasta'.");
        }
        if (desde != null && hasta.isBefore(desde)) {
            throw new BadRequestException("'hasta' no puede ser anterior a 'desde'.");
        }
    }

    private void validarRequest(AsaltoRequest req) {
        if (req == null) throw new BadRequestException("Body requerido.");
        if (req.getFechaAsalto() == null) throw new BadRequestException("fechaAsalto es obligatoria.");
        if (req.getSucursalId() == null) throw new BadRequestException("sucursalId es obligatorio.");
        if (req.getPersonaDetenidaId() == null) throw new BadRequestException("personaDetenidaId es obligatorio.");
    }

    private AsaltoResponse toResponse(Asalto a) {
        AsaltoResponse r = new AsaltoResponse();
        r.setId(a.getId());
        r.setCodigo(a.getCodigo());
        r.setFechaAsalto(a.getFechaAsalto());


        //Mapeamos la lista de personas al response
        if (a.getPersonas() != null) {
            r.setPersonas(a.getPersonas().stream().map(p -> {
                PersonaDetenidaResponse pr = new PersonaDetenidaResponse();
                pr.setId(p.getId());
                pr.setCodigo(p.getCodigo());
                pr.setNombre(p.getNombre());
                pr.setApellido(p.getApellido());
                return pr;
            }).collect(Collectors.toList()));
        }

        return r;

    }

}
