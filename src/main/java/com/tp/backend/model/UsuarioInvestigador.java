package com.tp.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("INVESTIGADOR")
public class UsuarioInvestigador extends Usuario {
    @Override public String getRol() { return "INVESTIGADOR"; }
}
