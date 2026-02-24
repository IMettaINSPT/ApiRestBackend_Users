package com.tp.backend.service;

import com.tp.backend.dto.asalto.*;
import com.tp.backend.dto.sucursal.SucursalResponse;
import com.tp.backend.dto.personaDetenida.PersonaDetenidaResponse;
import com.tp.backend.dto.banda.BandaResponse; // Importado para el mapeo
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
        // Validamos la lógica de fechas, pero ya no exigimos sucursalId
        validarFiltros(fecha, desde, hasta);

        // Llamamos al método dinámico del repositorio (debes crearlo en el Repository)
        List<Asalto> asaltos = asaltoRepository.filtrar(sucursalId, fecha, desde, hasta);

        return asaltos.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<AsaltoResponse> listarPorPersonaDetenida(Long personaDetenidaId) {
        return asaltoRepository.findByPersonas_Id(personaDetenidaId)
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

        List<PersonaDetenida> personas = personaDetenidaRepository.findAllById(req.getPersonaDetenidaIds());
        if (personas.isEmpty()) {
            throw new BadRequestException("Debe seleccionar al menos una persona válida.");
        }

        Asalto a = new Asalto();
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

        List<PersonaDetenida> personas = personaDetenidaRepository.findAllById(req.getPersonaDetenidaIds());
        if (personas.isEmpty()) {
            throw new BadRequestException("Debe seleccionar al menos una persona válida.");
        }

        a.setCodigo(req.getCodigo());
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

    // ---------- HELPERS ----------
    private void validarFiltros(LocalDate fecha, LocalDate desde, LocalDate hasta) {
        // Permitimos que se use fecha exacta O rango, pero no ambos mezclados
        if (fecha != null && (desde != null || hasta != null)) {
            throw new BadRequestException("No se puede usar 'fecha exacta' junto con un período 'desde/hasta'.");
        }

        // Si se ingresan ambos límites del rango, validamos que sean lógicos
        if (desde != null && hasta != null && hasta.isBefore(desde)) {
            throw new BadRequestException("Rango de fechas inválido: la fecha 'desde' no puede ser posterior a la fecha 'hasta'.");
        }

        // Nota: Ahora se permite enviar solo 'desde' (ej: desde hoy en adelante)
        // o solo 'hasta' (ej: todo lo ocurrido hasta ayer) sin errores.
    }

    private void validarRequest(AsaltoRequest req) {
        if (req == null) throw new BadRequestException("Body requerido.");
        if (req.getFechaAsalto() == null) throw new BadRequestException("fechaAsalto es obligatoria.");
        if (req.getSucursalId() == null) throw new BadRequestException("sucursalId es obligatorio.");
        if (req.getPersonaDetenidaIds() == null || req.getPersonaDetenidaIds().isEmpty()) {
            throw new BadRequestException("Debe seleccionar al menos un detenido.");
        }
    }

    private AsaltoResponse toResponse(Asalto a) {
        AsaltoResponse r = new AsaltoResponse();
        r.setId(a.getId());
        r.setCodigo(a.getCodigo());
        r.setFechaAsalto(a.getFechaAsalto());

        if (a.getSucursal() != null) {
            Sucursal s = a.getSucursal();
            r.setSucursal(new SucursalResponse(
                    s.getId(),
                    s.getCodigo(),
                    s.getDomicilio(),
                    s.getNroEmpleados(),
                    s.getBanco() != null ? s.getBanco().getId() : null,
                    s.getBanco() != null ? s.getBanco().getCodigo() : null
            ));
        }

        if (a.getPersonas() != null) {
            List<PersonaDetenidaResponse> listaPersonaDTO = a.getPersonas().stream()
                    .map(p -> {
                        // Mapeamos la banda de la entidad a su DTO correspondiente
                        BandaResponse bandaDTO = null;
                        if (p.getBanda() != null) {
                            bandaDTO = new BandaResponse();
                            bandaDTO.setId(p.getBanda().getId());
                            bandaDTO.setNumeroBanda(p.getBanda().getNumeroBanda());
                            bandaDTO.setNumeroMiembros(p.getBanda().getNumeroMiembros());
                        }

                        return new PersonaDetenidaResponse(
                                p.getId(),
                                p.getCodigo(),
                                p.getNombre(),
                                p.getApellido(),
                                bandaDTO, // Pasamos el DTO mapeado en lugar de null
                                null
                        );
                    })
                    .toList();
            r.setPersonas(listaPersonaDTO);
        }
        return r;
    }
}