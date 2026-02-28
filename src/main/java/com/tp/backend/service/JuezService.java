package com.tp.backend.service;

import com.tp.backend.dto.juez.*;
import com.tp.backend.dto.juicio.JuicioResponse;
import com.tp.backend.dto.personaDetenida.PersonaDetenidaResponse;
import com.tp.backend.exception.BadRequestException;
import com.tp.backend.exception.NotFoundException;
import com.tp.backend.model.Juez;
import com.tp.backend.repository.JuezRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JuezService {

    private final JuezRepository repo;

    public JuezService(JuezRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<JuezResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public JuezResponse obtener(Long id) {
        Juez j = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Juez no encontrado: " + id));
        return toResponse(j);
    }

    @Transactional
    public JuezResponse crear(JuezRequest req) {
        if (repo.existsByClaveJuzgado(req.getClaveJuzgado())) {
            throw new BadRequestException("Ya existe un juez con código: " + req.getClaveJuzgado());
        }

        Juez j = new Juez();
        j.setClaveJuzgado(req.getClaveJuzgado());
        j.setNombre(req.getNombre());
        j.setApellido(req.getApellido());
        j.setAnosServicio(req.getAnosServicio());

        return toResponse(repo.save(j));
    }

    @Transactional
    public JuezResponse actualizar(Long id, JuezUpdateRequest req) {
        Juez j = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Juez no encontrado: " + id));

        if (!j.getClaveJuzgado().equals(req.getClaveJuzgado()) && repo.existsByClaveJuzgado(req.getClaveJuzgado())) {
            throw new BadRequestException("Ya existe un juez con código: " + req.getClaveJuzgado());
        }

        j.setClaveJuzgado(req.getClaveJuzgado());
        j.setNombre(req.getNombre());
        j.setApellido(req.getApellido());
        j.setAnosServicio(req.getAnosServicio());

        return toResponse(j);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Juez no encontrado: " + id);
        }
        repo.deleteById(id);
    }

    private JuezResponse toResponse(Juez j) {
        JuezResponse response = new JuezResponse(j.getId(), j.getClaveJuzgado(), j.getNombre(), j.getApellido(), j.getAnosServicio());

        if (j.getJuicios() != null) {
            response.setCantidadJuicios(j.getJuicios().size());

            List<JuicioResponse> listaJuiciosDto = j.getJuicios().stream()
                    .map(juicio -> {
                        JuicioResponse jr = new JuicioResponse();
                        jr.setId(juicio.getId());
                        jr.setExpediente(juicio.getExpediente());
                        jr.setFechaJuicio(juicio.getFechaJuicio());
                        jr.setCondenado(juicio.isCondenado());

                        // --- CORRECCIÓN PARA RECORD CON 6 PARÁMETROS ---
                        if (juicio.getPersonaDetenida() != null) {
                            var p = juicio.getPersonaDetenida();

                            // Pasamos los 6 argumentos requeridos por el record:
                            // id, codigo, nombre, apellido, banda (null), asaltos (null)
                            jr.setPersona(new PersonaDetenidaResponse(
                                    p.getId(),
                                    p.getCodigo(),
                                    p.getNombre(),
                                    p.getApellido(),
                                    null, // banda: no es necesaria para la vista de jueces
                                    null  // asaltos: no son necesarios para la vista de jueces
                            ));
                        }

                        return jr;
                    })
                    .collect(Collectors.toList());

            response.setJuicios(listaJuiciosDto);
        } else {
            response.setCantidadJuicios(0);
        }

        return response;
    }
}