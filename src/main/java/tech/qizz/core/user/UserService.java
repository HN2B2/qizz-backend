package tech.qizz.core.user;

import tech.qizz.core.entity.User;
import tech.qizz.core.user.dto.GetAllUserResponse;
import tech.qizz.core.user.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
//    public GetAllUserResponse getAllUser(Integer page, Integer limit, String email);
    public UserResponse getUserById(Long id);
}
