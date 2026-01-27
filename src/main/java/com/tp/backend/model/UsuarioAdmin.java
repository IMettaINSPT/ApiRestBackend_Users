package com.tp.backend.model;

import com.tp.backend.config.RolEnum;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class UsuarioAdmin extends Usuario {
    @Override public RolEnum getRol() { return RolEnum.ADMIN; }
}
