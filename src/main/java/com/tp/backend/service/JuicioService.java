package com.tp.backend.service;

import com.tp.backend.dto.juicio.*;
import com.tp.backend.dto.juez.JuezResponse;
import com.tp.backend.dto.personaDetenida.PersonaDetenidaResponse;
import com.tp.backend.dto.asalto.AsaltoResponse;
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
    private final AsaltoRepository asaltoRepo; // Necesario para el Delito

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
        Juez juez = juezRepo.findById(req.getJuezId())
                .orElseThrow(() -> new NotFoundException("Juez no encontrado"));
        PersonaDetenida persona = personaRepo.findById(req.getPersonaDetenidaId())
                .orElseThrow(() -> new NotFoundException("Persona no encontrada"));
        Asalto asalto = asaltoRepo.findById(req.getAsaltoId())
                .orElseThrow(() -> new NotFoundException("Asalto/Delito no encontrado"));

        Juicio j = new Juicio();
        mapRequestToEntity(j, req.getExpediente(), req.getFechaJuicio(), req.getSituacionPenal(),
                req.getFechaInicioCondena(), req.getTiempoCondenaMeses(), juez, persona, asalto);

        return toResponse(juicioRepo.save(j));
    }

    @Transactional
    public JuicioResponse actualizar(Long id, JuicioUpdateRequest req) {
        Juicio j = juicioRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Juicio no encontrado"));

        Juez juez = juezRepo.findById(req.getJuezId()).orElseThrow();
        PersonaDetenida persona = personaRepo.findById(req.getPersonaDetenidaId()).orElseThrow();
        Asalto asalto = asaltoRepo.findById(req.getAsaltoId()).orElseThrow();

        mapRequestToEntity(j, req.getExpediente(), req.getFechaJuicio(), req.getSituacionPenal(),
                req.getFechaInicioCondena(), req.getTiempoCondenaMeses(), juez, persona, asalto);

        return toResponse(juicioRepo.save(j));
    }

    private void mapRequestToEntity(Juicio j, String exp, LocalDate fecha, ResultadoJuicio situacion,
                                    LocalDate fInicio, Integer meses, Juez juez, PersonaDetenida p, Asalto a) {
        j.setExpediente(exp);
        j.setFechaJuicio(fecha);
        j.setSituacionPenal(situacion);
        j.setJuez(juez);
        j.setPersonaDetenida(p);
        j.setAsalto(a);

        // Si es condenado guardamos las fechas, sino las limpiamos (null)
        if (situacion == ResultadoJuicio.CONDENADO) {
            j.setFechaInicioCondena(fInicio);
            j.setTiempoCondenaMeses(meses);
        } else {
            j.setFechaInicioCondena(null);
            j.setTiempoCondenaMeses(null);
        }
    }

    @Transactional
    public void eliminar(Long id) {
        if (!juicioRepo.existsById(id)) throw new NotFoundException("No existe");
        juicioRepo.deleteById(id);
    }

    private JuicioResponse toResponse(Juicio j) {
        JuicioResponse res = new JuicioResponse();
        res.setId(j.getId());
        res.setExpediente(j.getExpediente());
        res.setFechaJuicio(j.getFechaJuicio());
        res.setSituacionPenal(j.getSituacionPenal());

        // --- Lógica de Detalle de Pena ---
        res.setDetallePena(generarDetallePena(j));

        // --- Mapeo de Objetos Anidados ---
        // Aquí asumo que tienes mappers o servicios para Juez, Asalto y Persona
        // Si no, puedes instanciar el Response y setear los campos básicos:
        res.setJuez(new JuezResponse(j.getJuez().getId(), j.getJuez().getClaveJuzgado(), j.getJuez().getApellido()));
        res.setPersona(new PersonaResponse(j.getPersonaDetenida().getId(), j.getPersonaDetenida().getCodigo(), j.getPersonaDetenida().getApellido()));
        res.setAsalto(new AsaltoResponse(j.getAsalto().getId(), j.getAsalto().getCodigo(), j.getAsalto().getFecha()));

        return res;
    }

    private String generarDetallePena(Juicio j) {
        if (j.getSituacionPenal() == ResultadoJuicio.CONDENADO && j.getFechaInicioCondena() != null && j.getTiempoCondenaMeses() != null) {
            LocalDate fechaSalida = j.getFechaInicioCondena().plusMonths(j.getTiempoCondenaMeses());
            String fechaFmt = fechaSalida.format(DateTimeFormatter.ofPattern("MM/yyyy"));

            if (LocalDate.now().isAfter(fechaSalida)) {
                return String.format("Cumplió %d meses (salió en %s)", j.getTiempoCondenaMeses(), fechaFmt);
            } else {
                return String.format("Debe cumplir %d meses (sale en %s)", j.getTiempoCondenaMeses(), fechaFmt);
            }
        }
        return "N/A (Absuelto)";
    }
}