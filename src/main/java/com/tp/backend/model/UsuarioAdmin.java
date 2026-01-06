package com.tp.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class UsuarioAdmin extends Usuario {
    @Override public String getRol() { return "ADMIN"; }
}
