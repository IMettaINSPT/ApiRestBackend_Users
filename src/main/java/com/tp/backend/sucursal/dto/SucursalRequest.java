package com.tp.backend.sucursal.dto;

import jakarta.validation.constraints.*;

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
    public void setCodigo(String c) { this.codigo = c; }
    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String d) { this.domicilio = d; }
    public int getNroEmpleados() { return nroEmpleados; }
    public void setNroEmpleados(int n) { this.nroEmpleados = n; }
    public Long getBancoId() { return bancoId; }
    public void setBancoId(Long b) { this.bancoId = b; }
}