package com.tp.backend.juez.domain;

import com.tp.backend.model.Juicio;
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

    @OneToMany(mappedBy = "juez", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Juicio> juicios = new ArrayList<>();

    // Getters y Setters respetando los nombres originales
    public Long getId() { return id; }
    public String getClaveJuzgado() { return claveJuzgado; }
    public void setClaveJuzgado(String c) { this.claveJuzgado = c; }
    public String getNombre() { return nombre; }
    public void setNombre(String n) { this.nombre = n; }
    public String getApellido() { return apellido; }
    public void setApellido(String a) { this.apellido = a; }
    public Integer getAnosServicio() { return anosServicio; }
    public void setAnosServicio(Integer as) { this.anosServicio = as; }
    public List<Juicio> getJuicios() { return juicios; }
}