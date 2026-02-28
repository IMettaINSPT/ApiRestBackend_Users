package com.tp.backend.controller;
//cambios new
import com.tp.backend.dto.asalto.*;
import com.tp.backend.dto.personaDetenida.PersonaDetenidaResponse;
import com.tp.backend.service.AsaltoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.List;
//new new
@RestController
@RequestMapping("/api/asaltos")
@CrossOrigin(origins = "*")
public class AsaltoController {

    private final AsaltoService service;
    private static final Logger log = LoggerFactory.getLogger(AsaltoController.class);

    public AsaltoController(AsaltoService service) {
        this.service = service;
    }

    @GetMapping
    public List<AsaltoResponse> listar(
            @RequestParam(required = false) Long sucursalId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) {
        log.info("Get /api/asaltos/listar fecha{}, desde{}, hasta{}", fecha, desde, hasta);

        return service.listarConFiltros(sucursalId, fecha, desde, hasta);
    }

    @GetMapping("/{id}")
    public AsaltoResponse porId(@PathVariable Long id) {
        log.info("Get /api/asaltos/porId id{}",id);
        return service.buscarPorId(id);
    }

    // --- NUEVO ENDPOINT PARA FILTRADO DINÁMICO ---
    @GetMapping("/{id}/personas")
    public List<PersonaDetenidaResponse> listarPersonasPorAsalto(@PathVariable Long id) {
        log.info("Get /api/asaltos/{}/personas", id);
        // Aprovechamos el método buscarPorId que ya mapea las personas en el Response
        return service.buscarPorId(id).getPersonas();
    }

    @PostMapping
    public AsaltoResponse crear(@RequestBody AsaltoRequest req) {
        log.info("Post /api/asaltos/crear");

        return service.crear(req);
    }

    @PutMapping("/{id}")
    public AsaltoResponse actualizar(@PathVariable Long id, @RequestBody AsaltoRequest req) {
        log.info("Put /api/asaltos/actualizar id{}",id);

        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        log.info("Delete /api/asaltos/eliminar id{}",id);
        service.eliminar(id);
    }
}