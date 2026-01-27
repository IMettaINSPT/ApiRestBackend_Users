package com.tp.backend.controller;

import com.tp.backend.dto.banco.*;
import com.tp.backend.service.BancoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/bancos")
public class BancoController {

    private final BancoService service;
    private static final Logger log = LoggerFactory.getLogger(BancoController.class);

    public BancoController(BancoService service) {
        this.service = service;
    }

    @GetMapping
    public List<BancoResponse> listar() {
        log.info("Get /api/bancos/Listar");

        return service.listar();
    }

    @GetMapping("/{id}")
    public BancoResponse obtener(@PathVariable Long id) {
        log.info("Get /api/bancos/Obtener id{}",id);

        return service.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BancoResponse crear(@Valid @RequestBody BancoRequest req) {
        log.info("Post /api/bancos/crear codigo{} , domicilio{}",req.getCodigo(), req.getDomicilioCentral());

        return service.crear(req);
    }

    @PutMapping("/{id}")
    public BancoResponse actualizar(@PathVariable Long id, @Valid @RequestBody BancoUpdateRequest req) {
        log.info("Put /api/bancos/actualizar id{}",id);

        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        log.info("Delte /api/bancos/eliminar id{}",id);

        service.eliminar(id);
    }
}
