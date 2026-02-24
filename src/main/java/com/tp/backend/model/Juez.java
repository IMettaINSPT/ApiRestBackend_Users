package com.tp.backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "juez")
public class Juez {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String claveJuzgado;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(nullable = false, length = 80)
    private String apellido;

    @Column(nullable = false)
    private Integer anosServicio;

    // Relación OneToMany: Un juez tiene muchos juicios.
    // CAMBIO: Se cambió FetchType.LAZY a FetchType.EAGER para que los juicios viajen al Frontend
    @OneToMany(mappedBy = "juez", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Juicio> juicios = new ArrayList<>();

    public Juez() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClaveJuzgado() { return claveJuzgado; }
    public void setClaveJuzgado(String claveJuzgado) { this.claveJuzgado = claveJuzgado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public Integer getAnosServicio() { return anosServicio; }
    public void setAnosServicio(Integer anosServicio) { this.anosServicio = anosServicio; }

    // El getter devuelve la lista de entidades Juicio
    public List<Juicio> getJuicios() { return juicios; }
    public void setJuicios(List<Juicio> juicios) { this.juicios = juicios; }
}