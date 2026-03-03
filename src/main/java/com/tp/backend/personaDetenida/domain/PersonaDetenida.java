package com.tp.backend.personaDetenida.domain;

import com.tp.backend.banda.domain.Banda;
import com.tp.backend.asalto.domain.Asalto;
import com.tp.backend.vigilante.domain.Vigilante;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "personaDetenida")
public class PersonaDetenida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String codigo;

    @Column(nullable = false, length = 80)
    private String nombre;

    private boolean conTobillera;

    @Column(nullable = false, length = 100)
    private String apellido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banda_id")
    private Banda banda;

    @ManyToMany(mappedBy = "personas", fetch = FetchType.LAZY)
    private List<Asalto> asaltos = new ArrayList<>();

    // Getters y Setters respetando nombres originales
    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String c) { this.codigo = c; }
    public String getNombre() { return nombre; }
    public void setNombre(String n) { this.nombre = n; }
    public String getApellido() { return apellido; }
    public void setApellido(String a) { this.apellido = a; }
    public Banda getBanda() { return banda; }
    public void setBanda(Banda b) { this.banda = b; }
    public List<Asalto> getAsaltos() { return asaltos; }
    public void setAsaltos(List<Asalto> a) { this.asaltos = a; }

    public boolean isConTobillera() {
        return conTobillera;
    }

    public void setConTobillera(boolean conTobillera) {
        this.conTobillera = conTobillera;
    }
}
