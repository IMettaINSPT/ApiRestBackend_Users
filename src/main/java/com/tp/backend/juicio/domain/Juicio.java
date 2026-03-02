package com.tp.backend.juicio.domain;

import com.tp.backend.juez.domain.Juez;
import com.tp.backend.personaDetenida.domain.PersonaDetenida;
import com.tp.backend.model.Asalto;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "juicios") // 1. Cambiamos a 'juicios' (plural) para recuperar tus 12 registros
public class Juicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String expediente;

    @Column(name = "fecha_juicio", nullable = false) // 2. En tu tabla vieja se llama 'fecha_juicio'
    private LocalDate fechaJuicio;

    private boolean condenado;

    // 3. En tu tabla vieja los datos están en 'situacion_penal'
    @Column(name = "situacion_penal")
    private String resultado;

    @Column(name = "fecha_inicio_condena")
    private LocalDate fechaInicioCondena;

    @Column(name = "tiempo_condena_meses")
    private Integer tiempoCondenaMeses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "juez_id")
    private Juez juez;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_detenida_id")
    private PersonaDetenida personaDetenida;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asalto_id")
    private Asalto asalto;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExpediente() { return expediente; }
    public void setExpediente(String expediente) { this.expediente = expediente; }

    public LocalDate getFechaJuicio() { return fechaJuicio; }
    public void setFechaJuicio(LocalDate fechaJuicio) { this.fechaJuicio = fechaJuicio; }

    public boolean isCondenado() { return condenado; }
    public void setCondenado(boolean condenado) { this.condenado = condenado; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }

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