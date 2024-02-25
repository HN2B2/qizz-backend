package tech.qizz.core.auth;

import jakarta.servlet.http.HttpServletResponse;
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
import tech.qizz.core.user.dto.ProfileResponse;
import tech.qizz.core.util.Helper;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final Helper helper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthResponse login(LoginRequest body, HttpServletResponse response) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword()));
        User user = userRepository.findByEmail(body.getEmail())
            .orElseThrow(() -> new NotFoundException("User not found"));

        String token = jwtService.generateToken(user);
        jwtService.setJwtToCookie(response, token);
        jwtService.setUserDataToCookie(response, ProfileResponse.of(user));
        return AuthResponse.builder()
            .user(ProfileResponse.of(user))
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
        jwtService.setJwtToCookie(response, token);
        jwtService.setUserDataToCookie(response, ProfileResponse.of(savedUser));
        return AuthResponse
            .builder()
            .user(ProfileResponse.of(savedUser))
            .token(token)
            .build();
    }

    @Override
    public AuthResponse createGuest(CreateGuestRequest body, HttpServletResponse response) {
        String generatedRandomEmail = passwordEncoder.encode(body.getDisplayName()) + "@qizz.tech";
        User user = User.builder()
            .displayName(body.getDisplayName())
            .email(generatedRandomEmail)
            .role(UserRole.GUEST)
            .enabled(true)
            .banned(false)
            .build();
        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);
        jwtService.setJwtToCookie(response, token);
        jwtService.setUserDataToCookie(response, ProfileResponse.of(savedUser));
        return AuthResponse
            .builder()
            .user(ProfileResponse.of(savedUser))
            .token(token)
            .build();
    }
}
