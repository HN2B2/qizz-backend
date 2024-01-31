package tech.qizz.core.manageUser;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Configuration;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.constant.UserRole;

@DataJpaTest
@Configuration
@RequiredArgsConstructor
public class UserRepositoryTest {

    private final UserRepository userRepository;

    @Test
    public void testExistsByUsernameOrEmail() {
        User user = new User();
        user.setUsername("test");
        user.setEmail("test@abc.com");
        user.setDisplayName("test");
        user.setPassword("test");
        user.setRole(UserRole.USER);
        user.setBanned(false);
        user.setEnabled(true);

        User saveUser = userRepository.save(user);

        boolean exists = userRepository.existsByUsernameOrEmail("test", "test@abc.com");

        assert exists;
    }
}
