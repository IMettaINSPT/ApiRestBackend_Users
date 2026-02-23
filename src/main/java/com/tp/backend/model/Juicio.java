package com.tp.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "juicios") // Se recomienda plural para tablas
public class Juicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String expediente; // Agregado: para identificar el juicio

    @Column(name = "fecha_juicio", nullable = false)
    private LocalDate fechaJuicio; // Antes se llamaba 'fecha'

    @Enumerated(EnumType.STRING)
    @Column(name = "situacion_penal", nullable = false, length = 20)
    private ResultadoJuicio situacionPenal; // Antes se llamaba 'resultado'

    // --- CAMPOS NUEVOS PARA LA CONDENA ---
    @Column(name = "fecha_inicio_condena")
    private LocalDate fechaInicioCondena;

    @Column(name = "tiempo_condena_meses")
    private Integer tiempoCondenaMeses;

    // --- RELACIONES ---
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "juez_id", nullable = false)
    private Juez juez;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_detenida_id", nullable = false)
    private PersonaDetenida personaDetenida;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "asalto_id", nullable = false)
    private Asalto asalto; // Agregado: para saber qué delito se juzga

    // Constructor vacío
    public Juicio() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExpediente() { return expediente; }
    public void setExpediente(String expediente) { this.expediente = expediente; }

    public LocalDate getFechaJuicio() { return fechaJuicio; }
    public void setFechaJuicio(LocalDate fechaJuicio) { this.fechaJuicio = fechaJuicio; }

    public ResultadoJuicio getSituacionPenal() { return situacionPenal; }
    public void setSituacionPenal(ResultadoJuicio situacionPenal) { this.situacionPenal = situacionPenal; }

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