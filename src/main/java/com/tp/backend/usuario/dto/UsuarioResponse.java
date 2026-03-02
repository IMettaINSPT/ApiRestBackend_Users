package com.tp.backend.usuario.dto;

/**
 * DTO de respuesta para el usuario.
 * Se utiliza String en el campo 'rol' para que coincida exactamente
 * con el atributo 'rol' del UserResponse en el Frontend.
 */
public record UsuarioResponse(
        Long id,
        String codigo,
        String username,
        String rol,       // Cambiado de RolEnum a String para compatibilidad
        boolean enabled,
        Long vigilanteId,
        String vigilanteCodigo
) {}