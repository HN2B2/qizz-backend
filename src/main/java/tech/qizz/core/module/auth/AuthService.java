package tech.qizz.core.module.auth;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import tech.qizz.core.module.auth.dto.AuthResponse;
import tech.qizz.core.module.auth.dto.CheckResetTokenRequest;
import tech.qizz.core.module.auth.dto.CreateGuestRequest;
import tech.qizz.core.module.auth.dto.ForgotPasswordRequest;
import tech.qizz.core.module.auth.dto.LoginRequest;
import tech.qizz.core.module.auth.dto.RegisterRequest;
import tech.qizz.core.module.auth.dto.ResetPasswordRequest;
import tech.qizz.core.module.auth.dto.VerifyRequest;

public interface AuthService {


    AuthResponse login(LoginRequest body, HttpServletResponse response);

    void register(RegisterRequest body)
        throws MessagingException, UnsupportedEncodingException;

    void forgotPassword(ForgotPasswordRequest body)
        throws MessagingException, UnsupportedEncodingException;

    void checkResetToken(CheckResetTokenRequest body);

    AuthResponse resetPassword(ResetPasswordRequest body, HttpServletResponse response);

    AuthResponse createGuest(CreateGuestRequest body, HttpServletResponse response);

    AuthResponse verify(VerifyRequest body, HttpServletResponse response);

    void logout(HttpServletResponse response);
}
