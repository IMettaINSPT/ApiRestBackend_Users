package com.tp.backend.juicio.application;

import com.tp.backend.juicio.domain.*;
import com.tp.backend.juicio.mapper.JuicioMapper;
import com.tp.backend.juicio.dto.*;
import com.tp.backend.exception.NotFoundException;
import com.tp.backend.juez.infrastructure.JuezRepository;
import com.tp.backend.personaDetenida.infrastructure.PersonaDetenidaRepository;
import com.tp.backend.asalto.infrastructure.AsaltoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class JuicioService implements JuicioUseCase {
    private final JuicioPort port;
    private final JuicioMapper mapper;
    private final List<JuicioValidator<JuicioRequest>> validators;
    private final JuezRepository juezRepo;
    private final PersonaDetenidaRepository personaRepo;
    private final AsaltoRepository asaltoRepo;

    public JuicioService(JuicioPort port, JuicioMapper mapper, List<JuicioValidator<JuicioRequest>> validators,
                         JuezRepository juezRepo, PersonaDetenidaRepository personaRepo, AsaltoRepository asaltoRepo) {
        this.port = port;
        this.mapper = mapper;
        this.validators = validators;
        this.juezRepo = juezRepo;
        this.personaRepo = personaRepo;
        this.asaltoRepo = asaltoRepo;
    }

    @Override @Transactional(readOnly = true)
    public List<JuicioResponse> listar() {
        return port.listar().stream().map(mapper::toResponse).toList();
    }

    @Override @Transactional(readOnly = true)
    public JuicioResponse obtener(Long id) {
        return port.obtener(id).map(mapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Juicio no encontrado"));
    }

    @Override @Transactional
    public JuicioResponse crear(JuicioRequest req) {
        validators.forEach(v -> v.validar(req));
        var juez = juezRepo.findById(req.getJuezId()).orElseThrow(() -> new NotFoundException("Juez no encontrado"));
        var persona = personaRepo.findById(req.getPersonaDetenidaId()).orElseThrow(() -> new NotFoundException("Persona no encontrada"));
        var asalto = asaltoRepo.findById(req.getAsaltoId()).orElseThrow(() -> new NotFoundException("Asalto no encontrado"));

        Juicio j = mapper.toEntity(req, juez, persona, asalto);
        return mapper.toResponse(port.guardar(j));
    }

    @Override @Transactional
    public JuicioResponse actualizar(Long id, JuicioUpdateRequest req) {
        Juicio j = port.obtener(id).orElseThrow(() -> new NotFoundException("Juicio no encontrado"));
        j.setExpediente(req.getExpediente());
        j.setFechaJuicio(req.getFechaJuicio());
        j.setCondenado(req.isCondenado());
        j.setFechaInicioCondena(req.getFechaInicioCondena());
        j.setTiempoCondenaMeses(req.getTiempoCondenaMeses());
        return mapper.toResponse(port.guardar(j));
    }

    @Override @Transactional
    public void eliminar(Long id) {
        if (!port.existsById(id)) throw new NotFoundException("Juicio no encontrado");
        port.eliminar(id);
    }
}