package tech.qizz.core.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.constant.UserRole;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.user.dto.CreateUserRequest;
import tech.qizz.core.user.dto.GetAllUserResponse;
import tech.qizz.core.user.dto.UserResponse;
import tech.qizz.core.util.Helper;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Helper helper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public GetAllUserResponse getAllUser(
        Integer page,
        Integer limit,
        String keyword,
        UserRole role,
        Boolean banned,
        String order,
        String sort
    ) {
        Sort sortType = sort.equalsIgnoreCase("asc") ? Sort.by(order) : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page - 1, limit, sortType);
        Page<User> users = userRepository.findUsersByKeywordAndRoleAndBanned(
            keyword,
            role,
            banned,
            pageable
        );
        return GetAllUserResponse.of(users);
    }

    @Override
    public UserResponse getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserResponse::of)
            .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public UserResponse createUser(CreateUserRequest body) {
        User user = User.builder()
            .displayName(helper.generateUsername())
            .email(body.getEmail())
            .username(body.getUsername())
            .password(passwordEncoder.encode(body.getPassword()))
            .role(UserRole.USER)
            .banned(false)
            .build();
        return UserResponse.of(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(Long id, CreateUserRequest body) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
//        user.get().setDisplayName(body.getDisplayName());
        return UserResponse.of(userRepository.save(user.get()));
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        userRepository.delete(user.get());
    }
}
