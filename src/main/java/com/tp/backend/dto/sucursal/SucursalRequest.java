package com.tp.backend.dto.sucursal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SucursalRequest {

    @NotBlank
    @Size(max = 30)
    private String codigo;

    @NotBlank
    @Size(max = 160)
    private String domicilio;

    @Min(value = 1, message = "La sucursal debe tener al menos 1 empleado")
    @Max(value = 100, message = "La sucursal no puede tener más de 100 empleados")
    private int nroEmpleados;

    @NotNull
    private Long bancoId;

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public int getNroEmpleados() { return nroEmpleados; }
    public void setNroEmpleados(int nroEmpleados) { this.nroEmpleados = nroEmpleados; }

    public Long getBancoId() { return bancoId; }
    public void setBancoId(Long bancoId) { this.bancoId = bancoId; }
}