package com.tp.backend.dto.juez;

public class JuezResponse {

    private Long id;
    private String codigo;
    private String nombre;
    private String apellido;

    public JuezResponse(Long id, String codigo, String nombre, String apellido) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
}
