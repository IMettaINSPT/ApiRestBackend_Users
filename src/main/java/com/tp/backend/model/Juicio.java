package com.tp.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "juicio")
public class Juicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ResultadoJuicio resultado;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "juez_id", nullable = false)
    private Juez juez;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_detenida_id", nullable = false)
    private PersonaDetenida personaDetenida;

    public Long getId() { return id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public ResultadoJuicio getResultado() { return resultado; }
    public void setResultado(ResultadoJuicio resultado) { this.resultado = resultado; }

    public Juez getJuez() { return juez; }
    public void setJuez(Juez juez) { this.juez = juez; }

    public PersonaDetenida getPersonaDetenida() { return personaDetenida; }
    public void setPersonaDetenida(PersonaDetenida personaDetenida) {
        this.personaDetenida = personaDetenida;
    }
}

