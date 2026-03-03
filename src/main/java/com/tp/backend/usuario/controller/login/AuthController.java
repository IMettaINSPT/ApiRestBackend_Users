package com.tp.backend.usuario.controller.login;

import com.tp.backend.usuario.application.login.AuthUseCase;
import com.tp.backend.usuario.application.UsuarioUseCase; // Importación necesaria
import com.tp.backend.usuario.dto.login.LoginRequest;
import com.tp.backend.usuario.dto.login.LoginResponse;
import com.tp.backend.usuario.dto.UsuarioResponse; // Importación para el método /me
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthUseCase authUseCase;
    private final UsuarioUseCase usuarioUseCase; // <-- Agregamos el campo que faltaba

    // Actualizamos el constructor para inyectar ambos servicios
    public AuthController(AuthUseCase authUseCase, UsuarioUseCase usuarioUseCase) {
        this.authUseCase = authUseCase;
        this.usuarioUseCase = usuarioUseCase;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {
        return authUseCase.login(req);
    }

    @GetMapping("/me")
    public UsuarioResponse me() {
        // Ahora sí, usuarioUseCase está disponible para ser usado
        return usuarioUseCase.obtenerPerfilAutenticado();
    }
}