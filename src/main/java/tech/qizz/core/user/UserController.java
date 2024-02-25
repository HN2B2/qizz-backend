package tech.qizz.core.user;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.qizz.core.annotation.RequestUser;
import tech.qizz.core.entity.User;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.user.dto.ChangePasswordRequest;
import tech.qizz.core.user.dto.ProfileResponse;
import tech.qizz.core.user.dto.UpsertProfileRequest;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'STAFF', 'ADMIN')")
    public ResponseEntity<ProfileResponse> getUser(@RequestUser User requestUser) {
        return new ResponseEntity<>(ProfileResponse.of(requestUser), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('USER', 'STAFF', 'ADMIN')")
    public ResponseEntity<ProfileResponse> updateUser(
        @RequestUser User requestUser,
        @Valid @RequestBody UpsertProfileRequest body,
        BindingResult result,
        HttpServletResponse response
    ) {
        if (result.hasErrors()) {
            throw new BadRequestException("Invalid request");
        }
        return new ResponseEntity<>(
            userService.updateProfile(requestUser.getUserId(), body, response),
            HttpStatus.OK);
    }

    @PutMapping("/change-password")
    @PreAuthorize("hasAnyAuthority('USER', 'STAFF', 'ADMIN')")
    public ResponseEntity<ProfileResponse> changePassword(
        @RequestUser User requestUser,
        @Valid @RequestBody ChangePasswordRequest body
    ) {
        return new ResponseEntity<>(userService.changePassword(requestUser.getUserId(), body),
            HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<User> getUserTest(@RequestUser User requestUser) {
        return new ResponseEntity<>(requestUser, HttpStatus.OK);
    }
}
