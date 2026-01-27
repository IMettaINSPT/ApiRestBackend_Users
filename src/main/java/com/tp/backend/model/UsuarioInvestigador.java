package com.tp.backend.model;

import com.tp.backend.config.RolEnum;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("INVESTIGADOR")
public class UsuarioInvestigador extends Usuario {
    @Override public RolEnum getRol() { return RolEnum.INVESTIGADOR; }
}
