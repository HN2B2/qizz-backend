package tech.qizz.core.manageUser;

import tech.qizz.core.entity.constant.UserRole;
import tech.qizz.core.manageUser.dto.CreateUserRequest;
import tech.qizz.core.manageUser.dto.GetAllUserResponse;
import tech.qizz.core.manageUser.dto.UpdateUserRequest;
import tech.qizz.core.manageUser.dto.UsersResponse;

public interface UsersService {

    public GetAllUserResponse getAllUser(
        Integer page,
        Integer limit,
        String keyword,
        UserRole role,
        Boolean banned,
        String order,
        String sort
    );

    public UsersResponse getUserById(Long id);

    public UsersResponse createUser(CreateUserRequest body);

    public UsersResponse updateUser(Long id, UpdateUserRequest body);

    public void deleteUser(Long id);
}
