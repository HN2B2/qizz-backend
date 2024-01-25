package tech.qizz.core.listener;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.constant.UserRole;
import tech.qizz.core.user.UserRepository;
import tech.qizz.core.util.Helper;

@Component
@RequiredArgsConstructor
public class AdminAccountInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Helper helper;

    private String randomPassword() {
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "1234567890";
        String specialChars = "!@#$%^&*()";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            password.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
        }
        for (int i = 0; i < 2; i++) {
            password.append(upperCase.charAt(random.nextInt(upperCase.length())));
        }
        password.append(numbers.charAt(random.nextInt(numbers.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));

        return password.toString();
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        boolean adminExists = userRepository.existsByRole(UserRole.ADMIN);

        if (!adminExists) {
            String adminUsername = "admin";
            String adminPassword = randomPassword();
            String displayName = helper.generateUsername();
            User admin = User.builder()
                .email(adminUsername + "@qizz.tech")
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .displayName(displayName)
                .banned(false)
                .enabled(true)
                .role(UserRole.ADMIN)
                .build();
            userRepository.save(admin);

            System.out.println(
                "Default admin account created. Email: " + adminUsername + "@qizz.tech, Password: "
                    + adminPassword);
        }
    }
}