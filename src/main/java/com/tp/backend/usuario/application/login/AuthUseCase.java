package com.tp.backend.usuario.application.login;

import com.tp.backend.usuario.dto.login.LoginRequest;
import com.tp.backend.usuario.dto.login.LoginResponse;

public interface AuthUseCase {
    LoginResponse login(LoginRequest req);
}