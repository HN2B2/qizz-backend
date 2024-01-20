package tech.qizz.core.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.User;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.user.dto.GetAllUserResponse;
import tech.qizz.core.user.dto.UserResponse;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

//    @Override
//    public GetAllUserResponse getAllUser() {
//    }

    @Override
    public UserResponse getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserResponse::of).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
