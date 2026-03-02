package com.tp.backend.juicio.mapper;

import com.tp.backend.juicio.domain.Juicio;
import com.tp.backend.juicio.dto.JuicioRequest;
import com.tp.backend.juicio.dto.JuicioResponse;
import com.tp.backend.juez.domain.Juez;
import com.tp.backend.personaDetenida.domain.PersonaDetenida;
import com.tp.backend.model.Asalto;
import com.tp.backend.juez.dto.JuezResponse;
import com.tp.backend.personaDetenida.dto.PersonaDetenidaResponse;
import com.tp.backend.dto.asalto.AsaltoResponse;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class JuicioMapper {

    public JuicioResponse toResponse(Juicio j) {
        JuicioResponse res = new JuicioResponse();
        res.setId(j.getId());
        res.setExpediente(j.getExpediente());
        res.setFechaJuicio(j.getFechaJuicio());
        res.setCondenado(j.isCondenado());
        res.setFechaInicioCondena(j.getFechaInicioCondena());
        res.setTiempoCondenaMeses(j.getTiempoCondenaMeses());
        res.setDetallePena(generarDetallePena(j));

        if (j.getJuez() != null) {
            res.setJuez(new JuezResponse(j.getJuez().getId(), j.getJuez().getClaveJuzgado(), j.getJuez().getNombre(), j.getJuez().getApellido(), j.getJuez().getAnosServicio()));
        }

        if (j.getPersonaDetenida() != null) {
            var p = j.getPersonaDetenida();
            res.setPersona(new PersonaDetenidaResponse(p.getId(), p.getCodigo(), p.getNombre(), p.getApellido(), null, null));
        }

        if (j.getAsalto() != null) {
            AsaltoResponse a = new AsaltoResponse();
            a.setId(j.getAsalto().getId());
            a.setCodigo(j.getAsalto().getCodigo());
            a.setFechaAsalto(j.getAsalto().getFechaAsalto());
            res.setAsalto(a);
        }
        return res;
    }

    public Juicio toEntity(JuicioRequest req, Juez j, PersonaDetenida p, Asalto a) {
        Juicio entidad = new Juicio();
        entidad.setExpediente(req.getExpediente());
        entidad.setFechaJuicio(req.getFechaJuicio());
        entidad.setCondenado(req.isCondenado());

        // --- ESTO SOLUCIONA EL ERROR 500 ---
        // Asignamos el valor que el ENUM de la base de datos está esperando
        entidad.setResultado(req.isCondenado() ? "CONDENADO" : "ABSUELTO");
        // ------------------------------------

        entidad.setFechaInicioCondena(req.getFechaInicioCondena());
        entidad.setTiempoCondenaMeses(req.getTiempoCondenaMeses());
        entidad.setJuez(j);
        entidad.setPersonaDetenida(p);
        entidad.setAsalto(a);
        return entidad;
    }

    private String generarDetallePena(Juicio j) {
        if (j.isCondenado() && j.getFechaInicioCondena() != null && j.getTiempoCondenaMeses() != null) {
            LocalDate fechaSalida = j.getFechaInicioCondena().plusMonths(j.getTiempoCondenaMeses());
            String fechaFmt = fechaSalida.format(DateTimeFormatter.ofPattern("MM/yyyy"));
            return (LocalDate.now().isAfter(fechaSalida))
                    ? String.format("Cumplió %d meses (salió en %s)", j.getTiempoCondenaMeses(), fechaFmt)
                    : String.format("Condenado a %d meses (sale en %s)", j.getTiempoCondenaMeses(), fechaFmt);
        }
        return j.isCondenado() ? "Condenado (sin fechas definidas)" : "Absuelto";
    }
}