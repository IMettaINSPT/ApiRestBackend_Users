package com.tp.backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "personaDetenida")
public class PersonaDetenida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=30)
    private String codigo;

    @Column(nullable=false, length=80)
    private String nombre;

    @Column(nullable=false, length=100)
    private String apellido;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="banda_id")
    private Banda banda; // opcional

    // --- PARA EL HISTORIAL ---
    @OneToMany(mappedBy = "personaDetenida", fetch = FetchType.LAZY)
    private List<Asalto> asaltos;
    // --------------------------------------

    public Long getId() { return id; }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public Banda getBanda() { return banda; }
    public void setBanda(Banda banda) { this.banda = banda; }

    // --- GETTER Y SETTER DE ASALTOS ---
    public List<Asalto> getAsaltos() { return asaltos; }
    public void setAsaltos(List<Asalto> asaltos) { this.asaltos = asaltos; }


}
