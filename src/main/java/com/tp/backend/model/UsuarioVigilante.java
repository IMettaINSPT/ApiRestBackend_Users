package com.tp.backend.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("VIGILANTE")
public class UsuarioVigilante extends Usuario {

    // para asociar el perfil del vigilante al usuario
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="vigilante_id", unique = true) // sin nullable=false
    private Vigilante perfil;

    public Vigilante getPerfil() { return perfil; }
    public void setPerfil(Vigilante perfil) { this.perfil = perfil; }

    @Override public String getRol() { return "VIGILANTE"; }
}
