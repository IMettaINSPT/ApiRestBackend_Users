package com.tp.backend.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("VIGILANTE")
public class UsuarioVigilante extends Usuario {

    // Más adelante: relación 1 a 1 con Vigilante (perfil)
    // @OneToOne(optional=false, cascade=CascadeType.ALL)
    // @JoinColumn(name="vigilante_id")
    // private Vigilante perfil;

    @Override public String getRol() { return "VIGILANTE"; }
}
