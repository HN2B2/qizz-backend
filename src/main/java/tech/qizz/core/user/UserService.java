package tech.qizz.core.user;

import tech.qizz.core.user.dto.ChangePasswordRequest;
import tech.qizz.core.user.dto.ProfileResponse;
import tech.qizz.core.user.dto.UpdateProfileRequest;

public interface UserService {

    ProfileResponse updateProfile(Long id, UpdateProfileRequest body);

    ProfileResponse changePassword(Long id, ChangePasswordRequest body);
}
