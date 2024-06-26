package tech.qizz.core.module.manageUser;

import tech.qizz.core.entity.User;
import tech.qizz.core.entity.constant.UserRole;
import tech.qizz.core.module.manageUser.dto.CreateUserRequest;
import tech.qizz.core.module.manageUser.dto.GetAllUserResponse;
import tech.qizz.core.module.manageUser.dto.UpdateUserRequest;
import tech.qizz.core.module.manageUser.dto.UsersResponse;

import java.util.List;

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

    public GetAllUserResponse getAllUserEmails(String keyword, User user, List<String> manageBanks);

    public UsersResponse getUserById(Long id);

    public UsersResponse createUser(CreateUserRequest body);

    public UsersResponse updateUser(Long id, UpdateUserRequest body);

    public void deleteUser(Long id);
}
