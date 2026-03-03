package com.tp.backend.personaDetenida.application;

import com.tp.backend.personaDetenida.domain.*;
import com.tp.backend.personaDetenida.mapper.PersonaDetenidaMapper;
import com.tp.backend.personaDetenida.dto.*;
import com.tp.backend.banda.infrastructure.BandaRepository;
import com.tp.backend.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PersonaDetenidaService implements PersonaDetenidaUseCase {
    private final PersonaDetenidaPort port;
    private final PersonaDetenidaMapper mapper;
    private final List<PersonaDetenidaValidator<PersonaDetenidaRequest>> validators;
    private final BandaRepository bandaRepo;

    public PersonaDetenidaService(PersonaDetenidaPort port, PersonaDetenidaMapper mapper,
                                  List<PersonaDetenidaValidator<PersonaDetenidaRequest>> validators,
                                  BandaRepository bandaRepo) {
        this.port = port;
        this.mapper = mapper;
        this.validators = validators;
        this.bandaRepo = bandaRepo;
    }

    @Override @Transactional(readOnly = true)
    public List<PersonaDetenidaResponse> listar() {
        return port.listar().stream().map(mapper::toResponse).toList();
    }

    @Override @Transactional(readOnly = true)
    public PersonaDetenidaResponse obtener(Long id) {
        return port.obtener(id).map(mapper::toResponse)
                .orElseThrow(() -> new NotFoundException("PersonaDetenida no encontrada: " + id));
    }

    @Override @Transactional
    public PersonaDetenidaResponse crear(PersonaDetenidaRequest req) {
        validators.forEach(v -> v.validar(req));
        var banda = (req.getBandaId() == null) ? null :
                bandaRepo.findById(req.getBandaId()).orElseThrow(() -> new NotFoundException("Banda no encontrada"));
        PersonaDetenida p = mapper.toEntity(req, banda);
        return mapper.toResponse(port.guardar(p));
    }

    @Override @Transactional
    public PersonaDetenidaResponse actualizar(Long id, PersonaDetenidaUpdateRequest req) {
        PersonaDetenida p = port.obtener(id).orElseThrow(() -> new NotFoundException("PersonaDetenida no encontrada"));
        var banda = (req.getBandaId() == null) ? null :
                bandaRepo.findById(req.getBandaId()).orElseThrow(() -> new NotFoundException("Banda no encontrada"));

        p.setCodigo(req.getcodigo());
        p.setNombre(req.getNombre());
        p.setApellido(req.getApellido());
        p.setBanda(banda);
        p.setConTobillera(req.isConTobillera());
        return mapper.toResponse(port.guardar(p));
    }

    @Override @Transactional
    public void eliminar(Long id) {
        if (!port.existsById(id)) throw new NotFoundException("ID no existe");
        port.eliminar(id);
    }
}
