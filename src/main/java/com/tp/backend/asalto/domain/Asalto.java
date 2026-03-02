package com.tp.backend.asalto.domain;

import com.tp.backend.sucursal.domain.Sucursal;
import com.tp.backend.personaDetenida.domain.PersonaDetenida;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "asalto")
public class Asalto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", unique = true)
    private String codigo;

    @Column(name = "fecha_asalto", nullable = false)
    private LocalDate fechaAsalto;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "asalto_persona",
            joinColumns = @JoinColumn(name = "asalto_id"),
            inverseJoinColumns = @JoinColumn(name = "persona_id")
    )
    private List<PersonaDetenida> personas = new ArrayList<>();

    // Getters y Setters respetando el original [cite: 18-26]
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public LocalDate getFechaAsalto() { return fechaAsalto; }
    public void setFechaAsalto(LocalDate fechaAsalto) { this.fechaAsalto = fechaAsalto; }
    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }
    public List<PersonaDetenida> getPersonas() { return personas; }
    public void setPersonas(List<PersonaDetenida> personas) { this.personas = personas; }
}
