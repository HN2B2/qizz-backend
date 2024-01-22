package tech.qizz.core.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.User;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.user.dto.ChangePasswordRequest;
import tech.qizz.core.user.dto.ProfileResponse;
import tech.qizz.core.user.dto.UpdateProfileRequest;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProfileResponse updateProfile(Long id, UpdateProfileRequest body) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found"));
        modelMapper.map(body, user);
        return ProfileResponse.of(userRepository.save(user));
    }

    @Override
    public ProfileResponse changePassword(Long id, ChangePasswordRequest body) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found"));
        if (!user.getPassword().equals(body.getOldPassword())) {
            throw new BadRequestException("Wrong password");
        }
        user.setPassword(body.getNewPassword());
        return ProfileResponse.of(userRepository.save(user));
    }
}
