package com.tp.backend.dashboard.application;

import com.tp.backend.asalto.domain.AsaltoPort;
import com.tp.backend.banco.domain.BancoPort;
import com.tp.backend.banda.domain.BandaPort;
import com.tp.backend.contrato.domain.Contrato;
import com.tp.backend.contrato.domain.ContratoPort;
import com.tp.backend.dashboard.dto.DashboardSummaryResponse;
import com.tp.backend.juez.domain.JuezPort;
import com.tp.backend.juicio.domain.JuicioPort;
import com.tp.backend.personaDetenida.domain.PersonaDetenidaPort;
import com.tp.backend.sucursal.domain.SucursalPort;
import com.tp.backend.usuario.domain.UsuarioPort;
import com.tp.backend.vigilante.domain.VigilantePort;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService implements DashboardUseCase {

    private final BancoPort bancoPort;
    private final SucursalPort sucursalPort;
    private final ContratoPort contratoPort;
    private final VigilantePort vigilantePort;
    private final UsuarioPort usuarioPort;
    private final AsaltoPort asaltoPort;
    private final JuezPort juezPort;
    private final JuicioPort juicioPort;
    private final BandaPort bandaPort;
    private final PersonaDetenidaPort personaDetenidaPort;

    public DashboardService(
            BancoPort bancoPort, SucursalPort sucursalPort, ContratoPort contratoPort,
            VigilantePort vigilantePort, UsuarioPort usuarioPort, AsaltoPort asaltoPort,
            JuezPort juezPort, JuicioPort juicioPort, BandaPort bandaPort,
            PersonaDetenidaPort personaDetenidaPort) {
        this.bancoPort = bancoPort;
        this.sucursalPort = sucursalPort;
        this.contratoPort = contratoPort;
        this.vigilantePort = vigilantePort;
        this.usuarioPort = usuarioPort;
        this.asaltoPort = asaltoPort;
        this.juezPort = juezPort;
        this.juicioPort = juicioPort;
        this.bandaPort = bandaPort;
        this.personaDetenidaPort = personaDetenidaPort;
    }

    @Override
    public DashboardSummaryResponse getSummary() {
        var res = new DashboardSummaryResponse();

        // Totales usando Ports
        res.setBancos((long) bancoPort.listar().size());
        res.setSucursales((long) sucursalPort.listar().size());
        res.setContratos((long) contratoPort.listar().size());
        res.setVigilantes((long) vigilantePort.listar().size());
        res.setUsuarios((long) usuarioPort.listar().size());
        res.setJueces((long) juezPort.listar().size());
        res.setJuicios((long) juicioPort.listar().size());
        res.setBandas((long) bandaPort.listar().size());
        res.setDetenidos((long) personaDetenidaPort.listar().size());
        res.setAsaltos((long) asaltoPort.filtrar(null, null, null, null).size());

        // Usuarios por rol
        Map<String, Long> usuariosPorRol = usuarioPort.listar().stream()
                .collect(Collectors.groupingBy(u -> u.getRol().name(), Collectors.counting()));
        res.setUsuariosPorRol(usuariosPorRol);

        // Contratos con arma vs sin arma
        var contratos = contratoPort.listar();
        long conArma = contratos.stream().filter(Contrato::isConArma).count();
        Map<String, Long> contratosPorArma = new LinkedHashMap<>();
        contratosPorArma.put("CON_ARMA", conArma);
        contratosPorArma.put("SIN_ARMA", contratos.size() - conArma);
        res.setContratosPorArma(contratosPorArma);

        // Asaltos por mes (últimos 6 meses)
        YearMonth now = YearMonth.now();
        Map<YearMonth, Long> grouped = asaltoPort.filtrar(null, null, null, null).stream()
                .filter(a -> a.getFechaAsalto() != null)
                .map(a -> YearMonth.from(a.getFechaAsalto()))
                .filter(ym -> !ym.isBefore(now.minusMonths(5)))
                .collect(Collectors.groupingBy(ym -> ym, Collectors.counting()));

        List<DashboardSummaryResponse.MesCantidad> serie = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            YearMonth ym = now.minusMonths(i);
            serie.add(new DashboardSummaryResponse.MesCantidad(ym.toString(), grouped.getOrDefault(ym, 0L)));
        }
        res.setAsaltosPorMes(serie);

        return res;
    }
}