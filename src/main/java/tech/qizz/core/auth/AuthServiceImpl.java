package tech.qizz.core.auth;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.qizz.core.auth.dto.AuthResponse;
import tech.qizz.core.auth.dto.CreateGuestRequest;
import tech.qizz.core.auth.dto.LoginRequest;
import tech.qizz.core.auth.dto.RegisterRequest;
import tech.qizz.core.auth.dto.VerifyRequest;
import tech.qizz.core.auth.jwt.JwtService;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.constant.UserRole;
import tech.qizz.core.exception.BadRequestException;
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
    private final JavaMailSender mailSender;

    @Value("${client.url}")
    private String CLIENT_URL;

    @Value("${spring.mail.username}")
    private String FROM_EMAIL;

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
    public void register(RegisterRequest body)
        throws MessagingException, UnsupportedEncodingException {
        boolean exists = userRepository.existsByUsernameOrEmail(body.getUsername(),
            body.getEmail());
        if (exists) {
            throw new ConflictException("User already exists");
        }
        String verificationCode = RandomString.make(64);
        User user = User.builder()
            .displayName(helper.generateUsername())
            .email(body.getEmail())
            .username(body.getUsername())
            .password(passwordEncoder.encode(body.getPassword()))
            .role(UserRole.USER)
            .enabled(false)
            .banned(false)
            .verificationCode(verificationCode)
            .build();
        User savedUser = userRepository.save(user);
        sendVerificationEmail(savedUser, CLIENT_URL);
    }

    private void sendVerificationEmail(User user, String siteUrl)
        throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String senderName = "Quiz App";
        String subject = "[Qizz App] Please verify your registration";
        String content = "Dear [[name]],<br>"
            + "Please click the link below to verify your registration:<br>"
            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
            + "Thank you,<br>"
            + "Qizz App.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(FROM_EMAIL, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getObjectUsername());
        String verifyURL = siteUrl + "/auth/verify?token=" + user.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);
        mailSender.send(message);
    }

    @Override
    public AuthResponse createGuest(CreateGuestRequest body, HttpServletResponse response) {
        String generatedRandomEmail = RandomString.make(64);
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

    @Override
    public AuthResponse verify(VerifyRequest body, HttpServletResponse response) {
        User user = userRepository.getUserByVerificationCode(body.getToken()).orElseThrow(
            () -> new NotFoundException("Invalid Token")
        );

        if (user.isEnabled()) {
            throw new BadRequestException("User already verified");
        }

        user.setEnabled(true);
        user.setVerificationCode(null);
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        jwtService.setJwtToCookie(response, token);
        jwtService.setUserDataToCookie(response, ProfileResponse.of(user));
        return AuthResponse
            .builder()
            .user(ProfileResponse.of(user))
            .token(token)
            .build();
    }
}
