package com.tp.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "juicios")
public class Juicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String expediente;

    @Column(name = "fecha_juicio", nullable = false)
    private LocalDate fechaJuicio;

    // --- CAMBIO: De Enum a boolean ---
    @Column(name = "condenado", nullable = false)
    private boolean condenado; // true = CONDENADO, false = ABSUELTO

    @Column(name = "fecha_inicio_condena")
    private LocalDate fechaInicioCondena;

    @Column(name = "tiempo_condena_meses")
    private Integer tiempoCondenaMeses;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "juez_id", nullable = false)
    private Juez juez;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_detenida_id", nullable = false)
    private PersonaDetenida personaDetenida;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "asalto_id", nullable = false)
    private Asalto asalto;

    public Juicio() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExpediente() { return expediente; }
    public void setExpediente(String expediente) { this.expediente = expediente; }

    public LocalDate getFechaJuicio() { return fechaJuicio; }
    public void setFechaJuicio(LocalDate fechaJuicio) { this.fechaJuicio = fechaJuicio; }

    // --- Getter y Setter para el boolean ---
    public boolean isCondenado() { return condenado; }
    public void setCondenado(boolean condenado) { this.condenado = condenado; }

    public LocalDate getFechaInicioCondena() { return fechaInicioCondena; }
    public void setFechaInicioCondena(LocalDate fechaInicioCondena) { this.fechaInicioCondena = fechaInicioCondena; }

    public Integer getTiempoCondenaMeses() { return tiempoCondenaMeses; }
    public void setTiempoCondenaMeses(Integer tiempoCondenaMeses) { this.tiempoCondenaMeses = tiempoCondenaMeses; }

    public Juez getJuez() { return juez; }
    public void setJuez(Juez juez) { this.juez = juez; }

    public PersonaDetenida getPersonaDetenida() { return personaDetenida; }
    public void setPersonaDetenida(PersonaDetenida personaDetenida) { this.personaDetenida = personaDetenida; }

    public Asalto getAsalto() { return asalto; }
    public void setAsalto(Asalto asalto) { this.asalto = asalto; }
}