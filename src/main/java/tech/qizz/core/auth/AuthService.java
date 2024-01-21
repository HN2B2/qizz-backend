package tech.qizz.core.auth;

import tech.qizz.core.auth.dto.AuthResponse;
import tech.qizz.core.auth.dto.LoginRequest;
import tech.qizz.core.auth.dto.RegisterRequest;

public interface AuthService {


    public AuthResponse login(LoginRequest body);

    public AuthResponse register(RegisterRequest body);
}
