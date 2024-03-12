package tech.qizz.core.auth;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import tech.qizz.core.auth.dto.AuthResponse;
import tech.qizz.core.auth.dto.CreateGuestRequest;
import tech.qizz.core.auth.dto.LoginRequest;
import tech.qizz.core.auth.dto.RegisterRequest;
import tech.qizz.core.auth.dto.VerifyRequest;

public interface AuthService {


    public AuthResponse login(LoginRequest body, HttpServletResponse response);

    public void register(RegisterRequest body)
        throws MessagingException, UnsupportedEncodingException;

    public AuthResponse createGuest(CreateGuestRequest body, HttpServletResponse response);

    public AuthResponse verify(VerifyRequest body, HttpServletResponse response);
}
