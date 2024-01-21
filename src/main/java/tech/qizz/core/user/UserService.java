package tech.qizz.core.user;

import tech.qizz.core.entity.constant.UserRole;
import tech.qizz.core.user.dto.CreateUserRequest;
import tech.qizz.core.user.dto.GetAllUserResponse;
import tech.qizz.core.user.dto.UserResponse;

public interface UserService {

    public GetAllUserResponse getAllUser(
        Integer page,
        Integer limit,
        String keyword,
        UserRole role,
        Boolean banned,
        String order,
        String sort
    );

    public UserResponse getUserById(Long id);

    public UserResponse createUser(CreateUserRequest body);

    public UserResponse updateUser(Long id, CreateUserRequest body);

    public void deleteUser(Long id);

}
