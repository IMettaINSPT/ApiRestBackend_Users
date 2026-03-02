package com.tp.backend.usuario.application.login;

import com.tp.backend.config.RolEnum;
import com.tp.backend.usuario.domain.Usuario;
import com.tp.backend.usuario.domain.UsuarioPort; // Usamos el Port del dominio
import com.tp.backend.usuario.dto.login.LoginRequest;
import com.tp.backend.usuario.dto.login.LoginResponse;
import com.tp.backend.exception.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService implements AuthUseCase {

    private final UsuarioPort port; // Cambiado de Repository a Port para seguir el patrón
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public AuthService(UsuarioPort port, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.port = port;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public LoginResponse login(LoginRequest req) {
        // Buscamos a través del puerto [cite: 921]
        Usuario u = port.obtenerPorUsername(req.username)
                .orElseThrow(() -> new BadRequestException("Credenciales inválidas"));

        if (!passwordEncoder.matches(req.password, u.getPassword())) {
            throw new BadRequestException("Credenciales inválidas");
        }

        RolEnum role = u.getRol();

        Instant now = Instant.now();
        Instant exp = now.plusSeconds(60 * 60); // 1 hora [cite: 924]

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("tp-backend")
                .issuedAt(now)
                .expiresAt(exp)
                .subject(u.getUsername())
                .claim("role", role.name())
                .claim("roleId", role.getId())
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(token, exp, u.getUsername(), role.name(), role.getId());
    }
}