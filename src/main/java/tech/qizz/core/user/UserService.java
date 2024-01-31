package tech.qizz.core.user;

import tech.qizz.core.user.dto.ChangePasswordRequest;
import tech.qizz.core.user.dto.ProfileResponse;
import tech.qizz.core.user.dto.UpsertProfileRequest;

public interface UserService {

    ProfileResponse updateProfile(Long id, UpsertProfileRequest body);

    ProfileResponse changePassword(Long id, ChangePasswordRequest body);
}
