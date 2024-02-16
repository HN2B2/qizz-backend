package tech.qizz.core.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.qizz.core.auth.dto.AuthResponse;
import tech.qizz.core.auth.dto.CreateGuestRequest;
import tech.qizz.core.auth.dto.LoginRequest;
import tech.qizz.core.auth.dto.RegisterRequest;
import tech.qizz.core.auth.jwt.JwtService;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.constant.UserRole;
import tech.qizz.core.exception.ConflictException;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.manageUser.UserRepository;
import tech.qizz.core.user.dto.UserResponse;
import tech.qizz.core.util.Helper;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final Helper helper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper mapper;

    private void setJwtToCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private void setUserDataToCookie(HttpServletResponse response, UserResponse user) {
        try {
            String userJson = URLEncoder.encode(mapper.writeValueAsString(user),
                StandardCharsets.UTF_8);
            Cookie cookie = new Cookie("user", userJson);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthResponse login(LoginRequest body, HttpServletResponse response) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword()));
        Optional<User> user = userRepository.findByEmail(body.getEmail());
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        String token = jwtService.generateToken(user.get());
        setJwtToCookie(response, token);
        setUserDataToCookie(response, UserResponse.of(user.get()));
        return AuthResponse.builder()
            .user(UserResponse.of(user.get()))
            .token(token)
            .build();
    }

    @Override
    public AuthResponse register(RegisterRequest body, HttpServletResponse response) {
        boolean exists = userRepository.existsByUsernameOrEmail(body.getUsername(),
            body.getEmail());
        if (exists) {
            throw new ConflictException("User already exists");
        }
        User user = User.builder()
            .displayName(helper.generateUsername())
            .email(body.getEmail())
            .username(body.getUsername())
            .password(passwordEncoder.encode(body.getPassword()))
            .role(UserRole.USER)
            .enabled(true)
            .banned(false)
            .build();
        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);
        setJwtToCookie(response, token);
        setUserDataToCookie(response, UserResponse.of(savedUser));
        return AuthResponse
            .builder()
            .user(UserResponse.of(savedUser))
            .token(token)
            .build();
    }

    @Override
    public AuthResponse createGuest(CreateGuestRequest body, HttpServletResponse response) {
        User user = User.builder()
            .displayName(body.getDisplayName())
            .role(UserRole.GUEST)
            .enabled(true)
            .banned(false)
            .build();
        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);
        setJwtToCookie(response, token);
        setUserDataToCookie(response, UserResponse.of(savedUser));
        return AuthResponse
            .builder()
            .user(UserResponse.of(savedUser))
            .token(token)
            .build();
    }
}
