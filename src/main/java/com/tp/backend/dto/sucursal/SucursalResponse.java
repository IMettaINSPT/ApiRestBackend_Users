package com.tp.backend.dto.sucursal;

public class SucursalResponse {

    private Long id;
    private String codigo;
    private String domicilio;
    private int nroEmpleados;

    private Long bancoId;
    private String bancoCodigo;

    public SucursalResponse(Long id, String codigo, String domicilio, int nroEmpleados,
                            Long bancoId, String bancoCodigo) {
        this.id = id;
        this.codigo = codigo;
        this.domicilio = domicilio;
        this.nroEmpleados = nroEmpleados;
        this.bancoId = bancoId;
        this.bancoCodigo = bancoCodigo;
    }

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getDomicilio() { return domicilio; }
    public int getNroEmpleados() { return nroEmpleados; }
    public Long getBancoId() { return bancoId; }
    public String getBancoCodigo() { return bancoCodigo; }
}
