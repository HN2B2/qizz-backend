package tech.qizz.core.auth;

import jakarta.servlet.http.HttpServletResponse;
import tech.qizz.core.auth.dto.AuthResponse;
import tech.qizz.core.auth.dto.CreateGuestRequest;
import tech.qizz.core.auth.dto.LoginRequest;
import tech.qizz.core.auth.dto.RegisterRequest;

public interface AuthService {


    public AuthResponse login(LoginRequest body, HttpServletResponse response);

    public AuthResponse register(RegisterRequest body, HttpServletResponse response);

    public AuthResponse createGuest(CreateGuestRequest body, HttpServletResponse response);
}
