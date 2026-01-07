package com.tp.backend.dto.asalto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public interface AsaltoRequestBase {
    LocalDate getFecha();
    Long getSucursalId();
    Long getBandaId();
    Long getVigilanteId();
    Set<Long> getPersonasDetenidasIds();
    BigDecimal getMontoRobado();
}
