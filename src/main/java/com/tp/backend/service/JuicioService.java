package com.tp.backend.service;

import com.tp.backend.dto.juicio.*;
import com.tp.backend.exception.NotFoundException;
import com.tp.backend.model.*;
import com.tp.backend.repository.JuicioRepository;
import com.tp.backend.repository.JuezRepository;
import com.tp.backend.repository.PersonaDetenidaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JuicioService {

    private final JuicioRepository juicioRepo;
    private final JuezRepository juezRepo;
    private final PersonaDetenidaRepository personaRepo;

    public JuicioService(JuicioRepository juicioRepo,
                         JuezRepository juezRepo,
                         PersonaDetenidaRepository personaRepo) {
        this.juicioRepo = juicioRepo;
        this.juezRepo = juezRepo;
        this.personaRepo = personaRepo;
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
                .orElseThrow(() -> new NotFoundException("Juez no encontrado: " + req.getJuezId()));


        PersonaDetenida persona = personaRepo.findById(req.getPersonaDetenidaId())
                .orElseThrow(() -> new NotFoundException(
                        "Persona detenida no encontrada: " + req.getPersonaDetenidaId()));

        Juicio j = new Juicio();
        j.setFecha(req.getFecha());
        j.setResultado(req.getResultado());
        j.setJuez(juez);
        j.setPersonaDetenida(persona);

        return toResponse(juicioRepo.save(j));
    }

    @Transactional
    public JuicioResponse actualizar(Long id, JuicioUpdateRequest req) {
        Juicio j = juicioRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Juicio no encontrado: " + id));

        Juez juez = juezRepo.findById(req.getJuezId())
                .orElseThrow(() -> new NotFoundException("Juez no encontrado: " + req.getJuezId()));

        PersonaDetenida persona = personaRepo.findById(req.getPersonaDetenidaId())
                .orElseThrow(() -> new NotFoundException(
                        "Persona detenida no encontrada: " + req.getPersonaDetenidaId()));

        j.setFecha(req.getFecha());
        j.setResultado(req.getResultado());
        j.setJuez(juez);
        j.setPersonaDetenida(persona);

        return toResponse(j);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!juicioRepo.existsById(id)) {
            throw new NotFoundException("Juicio no encontrado: " + id);
        }
        juicioRepo.deleteById(id);
    }

    private JuicioResponse toResponse(Juicio j) {
        return new JuicioResponse(
                j.getId(),
                j.getFecha(),
                j.getResultado(),
                j.getJuez().getId(),
                j.getJuez().getCodigo(),
                j.getPersonaDetenida().getId()
        );
    }
}
