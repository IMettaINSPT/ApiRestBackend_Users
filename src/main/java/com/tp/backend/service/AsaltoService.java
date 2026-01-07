package com.tp.backend.service;

import com.tp.backend.dto.asalto.*;
import com.tp.backend.exception.NotFoundException;
import com.tp.backend.model.*;
import com.tp.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AsaltoService {

    private final AsaltoRepository asaltoRepo;
    private final SucursalRepository sucursalRepo;
    private final BandaRepository bandaRepo;
    private final VigilanteRepository vigilanteRepo;
    private final PersonaDetenidaRepository personaRepo;

    public AsaltoService(AsaltoRepository asaltoRepo,
                         SucursalRepository sucursalRepo,
                         BandaRepository bandaRepo,
                         VigilanteRepository vigilanteRepo,
                         PersonaDetenidaRepository personaRepo) {
        this.asaltoRepo = asaltoRepo;
        this.sucursalRepo = sucursalRepo;
        this.bandaRepo = bandaRepo;
        this.vigilanteRepo = vigilanteRepo;
        this.personaRepo = personaRepo;
    }

    @Transactional(readOnly = true)
    public List<AsaltoResponse> listar() {
        return asaltoRepo.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AsaltoResponse obtener(Long id) {
        Asalto a = asaltoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Asalto no encontrado: " + id));
        return toResponse(a);
    }

    @Transactional
    public AsaltoResponse crear(AsaltoRequest req) {

        Asalto a = construirAsalto(req, new Asalto());
        return toResponse(asaltoRepo.save(a));
    }

    @Transactional
    public AsaltoResponse actualizar(Long id, AsaltoUpdateRequest req) {

        Asalto a = asaltoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Asalto no encontrado: " + id));

        a.getPersonasDetenidas().clear();
        construirAsalto(req, a);
        return toResponse(a);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!asaltoRepo.existsById(id)) {
            throw new NotFoundException("Asalto no encontrado: " + id);
        }
        asaltoRepo.deleteById(id);
    }

    // 🔧 Método común
    private Asalto construirAsalto(AsaltoRequestBase req, Asalto a) {

        Sucursal sucursal = sucursalRepo.findById(req.getSucursalId())
                .orElseThrow(() -> new NotFoundException("Sucursal no encontrada: " + req.getSucursalId()));

        Banda banda = bandaRepo.findById(req.getBandaId())
                .orElseThrow(() -> new NotFoundException("Banda no encontrada: " + req.getBandaId()));

        Vigilante vigilante = vigilanteRepo.findById(req.getVigilanteId())
                .orElseThrow(() -> new NotFoundException("Vigilante no encontrado: " + req.getVigilanteId()));

        Set<PersonaDetenida> detenidos = new HashSet<>();
        for (Long pid : req.getPersonasDetenidasIds()) {
            PersonaDetenida p = personaRepo.findById(pid)
                    .orElseThrow(() -> new NotFoundException("Persona detenida no encontrada: " + pid));
            detenidos.add(p);
        }

        a.setFecha(req.getFecha());
        a.setMontoRobado(req.getMontoRobado());
        a.setSucursal(sucursal);
        a.setBanda(banda);
        a.setVigilante(vigilante);
        a.getPersonasDetenidas().addAll(detenidos);


        return a;
    }

    private AsaltoResponse toResponse(Asalto a) {
        Set<Long> detenidosIds = a.getPersonasDetenidas()
                .stream()
                .map(PersonaDetenida::getId)
                .collect(Collectors.toSet());

        return new AsaltoResponse(
                a.getId(),
                a.getFecha(),
                a.getMontoRobado(),
                a.getSucursal().getId(),
                a.getSucursal().getCodigo(),
                a.getBanda().getId(),
                a.getBanda().getNombre(),
                a.getVigilante().getId(),
                a.getVigilante().getCodigo(),
                detenidosIds
        );

    }
}
