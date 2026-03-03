package com.tp.backend.usuario.domain;

import com.tp.backend.config.RolEnum;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SUPERADMIN")

public class UsuarioSuperAdmin extends Usuario {
        @Override public RolEnum getRol() { return RolEnum.SUPERADMIN; }
}




