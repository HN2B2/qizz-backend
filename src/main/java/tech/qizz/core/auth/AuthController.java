package tech.qizz.core.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.qizz.core.annotation.RequestUser;
import tech.qizz.core.auth.dto.AuthResponse;
import tech.qizz.core.auth.dto.LoginRequest;
import tech.qizz.core.auth.dto.RegisterRequest;
import tech.qizz.core.entity.User;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.user.dto.UserResponse;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest body,
        BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException("Invalid request");
        }
        return new ResponseEntity<>(authService.login(body), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest body,
        BindingResult result) {
        if (result.hasErrors() || body == null) {
            throw new BadRequestException("Invalid request");
        }
        return new ResponseEntity<>(authService.register(body), HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(@RequestUser User user) {
        return new ResponseEntity<>(UserResponse.of(user), HttpStatus.OK);
    }
}
