package tech.qizz.core.user;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.UserMetadata;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.manageUser.UserRepository;
import tech.qizz.core.user.dto.ChangePasswordRequest;
import tech.qizz.core.user.dto.ProfileResponse;
import tech.qizz.core.user.dto.UpsertProfileMetadataRequest;
import tech.qizz.core.user.dto.UpsertProfileRequest;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Map<String, String> KEY_REGEX_MAP = Map.of(
        "avatarUrl", "^https?://.+\\.(png|jpg|jpeg|gif)$",
        "birthDate", "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}Z$",
        "sex", "^(male|female)$",
        "phoneNumber", "^\\d{10}$"
    );

    private boolean isValidMetadata(UpsertProfileMetadataRequest metadata) {
        String key = metadata.getKey();
        String value = metadata.getValue();

        if (!KEY_REGEX_MAP.containsKey(key)) {
            return false;
        }

        String regex = KEY_REGEX_MAP.get(key);

        return value.matches(regex);
    }

    @Override
    public ProfileResponse updateProfile(Long id, UpsertProfileRequest body) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found"));
        body.getMetadata().forEach(metadata -> {
            if (!isValidMetadata(metadata)) {
                throw new BadRequestException("Invalid metadata");
            }
        });
        user.setDisplayName(body.getDisplayName());
        user.setUsername(body.getUsername());
        body.getMetadata().forEach(metadata -> upsertMetadata(user, metadata));
        return ProfileResponse.of(userRepository.save(user));
    }

    private void upsertMetadata(User user, UpsertProfileMetadataRequest metadata) {
        UserMetadata userMetadata = findMetadataByKey(user, metadata.getKey());
        if (userMetadata == null) {
            userMetadata = new UserMetadata();
            userMetadata.setKey(metadata.getKey());
            userMetadata.setUser(user);
            user.getUserMetadatas().add(userMetadata);
        }
        userMetadata.setValue(metadata.getValue());
    }

    private UserMetadata findMetadataByKey(User user, String key) {
        return user.getUserMetadatas().stream()
            .filter(metadata -> metadata.getKey().equals(key))
            .findFirst()
            .orElse(null);
    }

    @Override
    public ProfileResponse changePassword(Long id, ChangePasswordRequest body) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found"));

        if (!passwordEncoder.matches(body.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Wrong password");
        }
        user.setPassword(body.getNewPassword());
        return ProfileResponse.of(userRepository.save(user));
    }
}
