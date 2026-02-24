package com.tp.backend.service;

import com.tp.backend.dto.dashboard.DashboardSummaryResponse;
import com.tp.backend.model.Contrato;
import com.tp.backend.repository.*;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final BancoRepository bancoRepository;
    private final SucursalRepository sucursalRepository;
    private final ContratoRepository contratoRepository;
    private final VigilanteRepository vigilanteRepository;
    private final UsuarioRepository usuarioRepository;
    private final AsaltoRepository asaltoRepository;

    public DashboardService(
            BancoRepository bancoRepository,
            SucursalRepository sucursalRepository,
            ContratoRepository contratoRepository,
            VigilanteRepository vigilanteRepository,
            UsuarioRepository usuarioRepository,
            AsaltoRepository asaltoRepository
    ) {
        this.bancoRepository = bancoRepository;
        this.sucursalRepository = sucursalRepository;
        this.contratoRepository = contratoRepository;
        this.vigilanteRepository = vigilanteRepository;
        this.usuarioRepository = usuarioRepository;
        this.asaltoRepository = asaltoRepository;
    }

    public DashboardSummaryResponse getSummary() {
        var res = new DashboardSummaryResponse();

        // Totales
        res.setBancos(bancoRepository.count());
        res.setSucursales(sucursalRepository.count());
        res.setContratos(contratoRepository.count());
        res.setVigilantes(vigilanteRepository.count());
        res.setUsuarios(usuarioRepository.count());

        // Usuarios por rol
        var usuarios = usuarioRepository.findAll();
        Map<String, Long> usuariosPorRol = usuarios.stream()
                .collect(Collectors.groupingBy(u -> u.getRol().name(), Collectors.counting()));
        res.setUsuariosPorRol(usuariosPorRol);

        // Contratos con arma vs sin arma
        var contratos = contratoRepository.findAll();
        long conArma = contratos.stream().filter(Contrato::isConArma).count();
        long sinArma = contratos.size() - conArma;
        Map<String, Long> contratosPorArma = new LinkedHashMap<>();
        contratosPorArma.put("CON_ARMA", conArma);
        contratosPorArma.put("SIN_ARMA", sinArma);
        res.setContratosPorArma(contratosPorArma);

        // Asaltos por mes (últimos 6 meses)
        var asaltos = asaltoRepository.findAll();
        YearMonth now = YearMonth.now();
        Map<YearMonth, Long> grouped = asaltos.stream()
                .filter(a -> a.getFechaAsalto() != null)
                .map(a -> YearMonth.from(a.getFechaAsalto()))
                .filter(ym -> !ym.isBefore(now.minusMonths(5))) // últimos 6 incluyendo actual
                .collect(Collectors.groupingBy(ym -> ym, Collectors.counting()));

        List<DashboardSummaryResponse.MesCantidad> serie = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            YearMonth ym = now.minusMonths(i);
            long cant = grouped.getOrDefault(ym, 0L);
            serie.add(new DashboardSummaryResponse.MesCantidad(ym.toString(), cant));
        }
        res.setAsaltosPorMes(serie);

        return res;
    }
}
