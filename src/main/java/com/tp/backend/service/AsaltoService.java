package com.tp.backend.service;

import com.tp.backend.dto.asalto.*;
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
            throw new IllegalArgumentException("Para filtrar por fecha/rango se requiere sucursalId.");
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
                .orElseThrow(() -> new RuntimeException("Asalto no encontrado: " + id));
        return toResponse(a);
    }

    @Transactional
    public AsaltoResponse crear(AsaltoRequest req) {
        validarRequest(req);

        Sucursal sucursal = sucursalRepository.findById(req.getSucursalId())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada: " + req.getSucursalId()));

        PersonaDetenida persona = personaDetenidaRepository.findById(req.getPersonaDetenidaId())
                .orElseThrow(() -> new RuntimeException("PersonaDetenida no encontrada: " + req.getPersonaDetenidaId()));

        Asalto a = new Asalto();
        a.setFechaAsalto(req.getFechaAsalto());
        a.setSucursal(sucursal);
        a.setPersonaDetenida(persona);

        return toResponse(asaltoRepository.save(a));
    }

    @Transactional
    public AsaltoResponse actualizar(Long id, AsaltoRequest req) {
        validarRequest(req);

        Asalto a = asaltoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asalto no encontrado: " + id));

        Sucursal sucursal = sucursalRepository.findById(req.getSucursalId())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada: " + req.getSucursalId()));

        PersonaDetenida persona = personaDetenidaRepository.findById(req.getPersonaDetenidaId())
                .orElseThrow(() -> new RuntimeException("PersonaDetenida no encontrada: " + req.getPersonaDetenidaId()));

        a.setFechaAsalto(req.getFechaAsalto());
        a.setSucursal(sucursal);
        a.setPersonaDetenida(persona);

        return toResponse(asaltoRepository.save(a));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!asaltoRepository.existsById(id)) {
            throw new RuntimeException("Asalto no encontrado: " + id);
        }
        asaltoRepository.deleteById(id);
    }

    // ---------- helpers ----------
    private void validarFiltros(LocalDate fecha, LocalDate desde, LocalDate hasta) {
        if (fecha != null && (desde != null || hasta != null)) {
            throw new IllegalArgumentException("No se puede usar 'fecha' junto con 'desde/hasta'.");
        }
        if ((desde == null) != (hasta == null)) {
            throw new IllegalArgumentException("Si usás rango, enviá 'desde' y 'hasta'.");
        }
        if (desde != null && hasta != null && hasta.isBefore(desde)) {
            throw new IllegalArgumentException("'hasta' no puede ser anterior a 'desde'.");
        }
    }

    private void validarRequest(AsaltoRequest req) {
        if (req == null) throw new IllegalArgumentException("Body requerido.");
        if (req.getFechaAsalto() == null) throw new IllegalArgumentException("fechaAsalto es obligatoria.");
        if (req.getSucursalId() == null) throw new IllegalArgumentException("sucursalId es obligatorio.");
        if (req.getPersonaDetenidaId() == null) throw new IllegalArgumentException("personaDetenidaId es obligatorio.");
    }

    private AsaltoResponse toResponse(Asalto a) {
        AsaltoResponse r = new AsaltoResponse();
        r.setId(a.getId());
        r.setFechaAsalto(a.getFechaAsalto());
        r.setSucursalId(a.getSucursal().getId());
        r.setPersonaDetenidaId(a.getPersonaDetenida().getId());
        return r;
    }
}
