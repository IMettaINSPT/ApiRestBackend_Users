package com.tp.backend.usuario.mapper;

import com.tp.backend.config.RolEnum;
import com.tp.backend.usuario.domain.UsuarioAdmin;
import com.tp.backend.usuario.domain.UsuarioInvestigador;
import com.tp.backend.usuario.domain.UsuarioVigilante;
import com.tp.backend.usuario.domain.Usuario;
import com.tp.backend.usuario.dto.UsuarioRequest;
import com.tp.backend.usuario.dto.UsuarioResponse;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponse toResponse(Usuario u) {
        if (u == null) return null;

        Long vigilanteId = null;
        String vigilanteCodigo = null;

        if (u instanceof UsuarioVigilante uv) {
            if (uv.getPerfil() != null) {
                vigilanteId = uv.getPerfil().getId();
                vigilanteCodigo = uv.getPerfil().getCodigo();
            }
        }

        return new UsuarioResponse(
                u.getId(),
                u.getCodigo(),
                u.getUsername(),
                u.getRol().name(),
                u.isEnabled(),
                vigilanteId,
                vigilanteCodigo
        );
    }

    /**
     * Crea la instancia correcta de la entidad basada en el rol del request.
     * @param req DTO con los datos de creación/actualización
     * @return Instancia de la subclase de Usuario correspondiente
     */
    public Usuario toEntity(UsuarioRequest req) {
        if (req == null) return null;

        // 1. Determinar el tipo de entidad a crear según el RolEnum
        RolEnum rol = RolEnum.valueOf(req.getRol().toUpperCase());

        Usuario usuario = switch (rol) {
            case ADMIN -> new UsuarioAdmin();
            case INVESTIGADOR -> new UsuarioInvestigador();
            case VIGILANTE -> new UsuarioVigilante();
        };

        // 2. Setear campos comunes
        usuario.setCodigo(req.getCodigo());
        usuario.setUsername(req.getUsername());
        // La password se setea usualmente en el Service tras el encode

        return usuario;
    }
}