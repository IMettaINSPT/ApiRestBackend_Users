package com.tp.backend.service;

import com.tp.backend.dto.juicio.*;
import com.tp.backend.dto.juez.JuezResponse;
import com.tp.backend.dto.personaDetenida.PersonaDetenidaResponse;
import com.tp.backend.dto.asalto.AsaltoResponse;
import com.tp.backend.dto.banda.BandaResponse;
import com.tp.backend.exception.NotFoundException;
import com.tp.backend.model.*;
import com.tp.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class JuicioService {

    private final JuicioRepository juicioRepo;
    private final JuezRepository juezRepo;
    private final PersonaDetenidaRepository personaRepo;
    private final AsaltoRepository asaltoRepo;

    public JuicioService(JuicioRepository juicioRepo,
                         JuezRepository juezRepo,
                         PersonaDetenidaRepository personaRepo,
                         AsaltoRepository asaltoRepo) {
        this.juicioRepo = juicioRepo;
        this.juezRepo = juezRepo;
        this.personaRepo = personaRepo;
        this.asaltoRepo = asaltoRepo;
    }

    @Transactional(readOnly = true)
    public List<JuicioResponse> listar() {
        return juicioRepo.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public JuicioResponse obtener(Long id) {
        Juicio j = juicioRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Juicio no encontrado: " + id));
        return toResponse(j);
    }

    @Transactional
    public JuicioResponse crear(JuicioRequest req) {
        Juez juez = juezRepo.findById(req.getJuezId()).orElseThrow();
        PersonaDetenida persona = personaRepo.findById(req.getPersonaDetenidaId()).orElseThrow();
        Asalto asalto = asaltoRepo.findById(req.getAsaltoId()).orElseThrow();

        // VALIDACIÓN: Fecha Juicio >= Fecha Asalto
        if (req.getFechaJuicio().isBefore(asalto.getFechaAsalto())) {
            throw new IllegalArgumentException("La fecha del juicio no puede ser anterior a la fecha del asalto (" + asalto.getFechaAsalto() + ")");
        }

        // VALIDACIÓN: Fecha Inicio Condena >= Fecha Juicio
        if (req.isCondenado() && req.getFechaInicioCondena() != null) {
            if (req.getFechaInicioCondena().isBefore(req.getFechaJuicio())) {
                throw new IllegalArgumentException("La fecha de inicio de condena no puede ser anterior a la fecha del juicio (" + req.getFechaJuicio() + ")");
            }
        }

        Juicio j = new Juicio();
        mapRequestToEntity(j, req.getExpediente(), req.getFechaJuicio(), req.isCondenado(),
                req.getFechaInicioCondena(), req.getTiempoCondenaMeses(), juez, persona, asalto);

        return toResponse(juicioRepo.save(j));
    }

    @Transactional
    public JuicioResponse actualizar(Long id, JuicioUpdateRequest req) {
        Juicio j = juicioRepo.findById(id).orElseThrow();
        Juez juez = juezRepo.findById(req.getJuezId()).orElseThrow();
        PersonaDetenida persona = personaRepo.findById(req.getPersonaDetenidaId()).orElseThrow();
        Asalto asalto = asaltoRepo.findById(req.getAsaltoId()).orElseThrow();

        // VALIDACIÓN: Fecha Juicio >= Fecha Asalto
        if (req.getFechaJuicio().isBefore(asalto.getFechaAsalto())) {
            throw new IllegalArgumentException("La fecha del juicio no puede ser anterior a la fecha del asalto (" + asalto.getFechaAsalto() + ")");
        }

        // VALIDACIÓN: Fecha Inicio Condena >= Fecha Juicio
        if (req.isCondenado() && req.getFechaInicioCondena() != null) {
            if (req.getFechaInicioCondena().isBefore(req.getFechaJuicio())) {
                throw new IllegalArgumentException("La fecha de inicio de condena no puede ser anterior a la fecha del juicio (" + req.getFechaJuicio() + ")");
            }
        }

        mapRequestToEntity(j, req.getExpediente(), req.getFechaJuicio(), req.isCondenado(),
                req.getFechaInicioCondena(), req.getTiempoCondenaMeses(), juez, persona, asalto);

        return toResponse(juicioRepo.save(j));
    }

    private void mapRequestToEntity(Juicio j, String exp, LocalDate fecha, boolean esCondenado,
                                    LocalDate fInicio, Integer meses, Juez juez, PersonaDetenida p, Asalto a) {
        j.setExpediente(exp);
        j.setFechaJuicio(fecha);
        j.setCondenado(esCondenado);
        j.setJuez(juez);
        j.setPersonaDetenida(p);
        j.setAsalto(a);

        if (esCondenado) {
            j.setFechaInicioCondena(fInicio);
            j.setTiempoCondenaMeses(meses);
        } else {
            j.setFechaInicioCondena(null);
            j.setTiempoCondenaMeses(null);
        }
    }

    @Transactional
    public void eliminar(Long id) {
        if (!juicioRepo.existsById(id)) throw new NotFoundException("No existe el juicio");
        juicioRepo.deleteById(id);
    }

    private JuicioResponse toResponse(Juicio j) {
        JuicioResponse res = new JuicioResponse();
        res.setId(j.getId());
        res.setExpediente(j.getExpediente());
        res.setFechaJuicio(j.getFechaJuicio());
        res.setCondenado(j.isCondenado());
        res.setDetallePena(generarDetallePena(j));

        res.setJuez(new JuezResponse(
                j.getJuez().getId(),
                j.getJuez().getClaveJuzgado(),
                j.getJuez().getNombre(),
                j.getJuez().getApellido(),
                j.getJuez().getAnosServicio()
        ));

        // --- Mapear la Banda dentro de la Persona ---
        PersonaDetenida p = j.getPersonaDetenida();
        BandaResponse bandaDto = null;

        if (p.getBanda() != null) {
            bandaDto = new BandaResponse(
                    p.getBanda().getId(),
                    p.getBanda().getNumeroBanda(),
                    null
            );
        }

        res.setPersona(new PersonaDetenidaResponse(
                p.getId(),
                p.getCodigo(),
                p.getNombre(),
                p.getApellido(),
                bandaDto,
                null
        ));

        AsaltoResponse asaltoDto = new AsaltoResponse();
        asaltoDto.setId(j.getAsalto().getId());
        asaltoDto.setCodigo(j.getAsalto().getCodigo());
        asaltoDto.setFechaAsalto(j.getAsalto().getFechaAsalto());
        res.setAsalto(asaltoDto);

        return res;
    }

    private String generarDetallePena(Juicio j) {
        if (j.isCondenado()
                && j.getFechaInicioCondena() != null
                && j.getTiempoCondenaMeses() != null) {

            LocalDate fechaSalida = j.getFechaInicioCondena().plusMonths(j.getTiempoCondenaMeses());
            String fechaFmt = fechaSalida.format(DateTimeFormatter.ofPattern("MM/yyyy"));

            if (LocalDate.now().isAfter(fechaSalida)) {
                return String.format("Cumplió %d meses (salió en %s)", j.getTiempoCondenaMeses(), fechaFmt);
            } else {
                return String.format("Debe cumplir %d meses (sale en %s)", j.getTiempoCondenaMeses(), fechaFmt);
            }
        }
        return " No presenta ";
    }
}