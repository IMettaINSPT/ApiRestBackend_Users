package com.tp.backend.service;

import com.tp.backend.dto.LoginRequest;
import com.tp.backend.dto.LoginResponse;
import com.tp.backend.exception.BadRequestException;
import com.tp.backend.model.Usuario;
import com.tp.backend.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public AuthService(UsuarioRepository repo, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public LoginResponse login(LoginRequest req) {

        Usuario u = repo.findByUsername(req.username)
                .orElseThrow(() -> new BadRequestException("Credenciales inválidas"));

        if (!passwordEncoder.matches(req.password, u.getPassword())) {
            throw new BadRequestException("Credenciales inválidas");
        }

        String roleName = u.getRole().getNombre(); // ADMIN / USER

        Instant now = Instant.now();
        Instant exp = now.plusSeconds(60 * 60); // 1 hora

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("tp-backend")
                .issuedAt(now)
                .expiresAt(exp)
                .subject(u.getUsername())
                .claim("role", roleName)
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(token, exp, u.getUsername(), roleName);
    }
}
