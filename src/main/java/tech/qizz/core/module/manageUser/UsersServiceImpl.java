package tech.qizz.core.module.manageUser;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.constant.UserRole;
import tech.qizz.core.exception.ConflictException;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.module.manageUser.dto.CreateUserRequest;
import tech.qizz.core.module.manageUser.dto.GetAllUserResponse;
import tech.qizz.core.module.manageUser.dto.UpdateUserRequest;
import tech.qizz.core.module.manageUser.dto.UsersResponse;
import tech.qizz.core.repository.UserRepository;
import tech.qizz.core.util.Helper;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;
    private final Helper helper;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

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
    public UsersResponse getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found"));
        return UsersResponse.of(user);
    }

    @Override
    public UsersResponse createUser(CreateUserRequest body) {
        boolean exists = userRepository.existsByUsernameOrEmail(body.getUsername(),
            body.getEmail());
        if (exists) {
            throw new ConflictException("Username or email already exists");
        }
        User user = User.builder()
            .displayName(helper.generateUsername())
            .email(body.getEmail())
            .username(body.getUsername())
            .password(passwordEncoder.encode(body.getPassword()))
            .role(UserRole.USER)
            .banned(false)
            .build();
        return UsersResponse.of(userRepository.save(user));
    }

    @Override
    public UsersResponse updateUser(Long id, UpdateUserRequest body) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found"));
        modelMapper.map(body, user);
        return UsersResponse.of(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        userRepository.delete(user.get());
    }

    @Override
    public GetAllUserResponse getAllUserEmails(String keyword, User user, List<String> manageBanks) {
        Pageable pageable = PageRequest.of(0, 5);
        Page<User> users = userRepository.findUserEmailsByKeyword(
                keyword,
                user.getEmail(),
                manageBanks,
                pageable
        );
        return GetAllUserResponse.of(users);
    }
}
