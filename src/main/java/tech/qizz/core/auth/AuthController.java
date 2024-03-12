package tech.qizz.core.auth;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.qizz.core.auth.dto.AuthResponse;
import tech.qizz.core.auth.dto.CreateGuestRequest;
import tech.qizz.core.auth.dto.LoginRequest;
import tech.qizz.core.auth.dto.RegisterRequest;
import tech.qizz.core.auth.dto.VerifyRequest;
import tech.qizz.core.exception.BadRequestException;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
        @Valid @RequestBody LoginRequest body,
        BindingResult result,
        HttpServletResponse response
    ) {
        if (result.hasErrors()) {
            throw new BadRequestException("Invalid request");
        }
        return new ResponseEntity<>(authService.login(body, response), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(
        @Valid @RequestBody RegisterRequest body,
        BindingResult result
    ) throws MessagingException, UnsupportedEncodingException {
        if (result.hasErrors() || body == null) {
            throw new BadRequestException("Invalid request");
        }
        authService.register(body);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/verify")
    public ResponseEntity<AuthResponse> verify(
        @Valid @RequestBody VerifyRequest body,
        BindingResult result,
        HttpServletResponse response
    ) {
        if (result.hasErrors() || body == null) {
            throw new BadRequestException("Invalid token");
        }
        return new ResponseEntity<>(authService.verify(body, response), HttpStatus.OK);
    }

    @PostMapping("/create-guest")
    public ResponseEntity<AuthResponse> createGuest(
        @Valid @RequestBody CreateGuestRequest body,
        BindingResult result,
        HttpServletResponse response
    ) {
        if (result.hasErrors() || body == null) {
            throw new BadRequestException("Invalid request");
        }
        return new ResponseEntity<>(authService.createGuest(body, response), HttpStatus.CREATED);
    }
}
