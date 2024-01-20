package tech.qizz.core.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.qizz.core.user.dto.GetAllUserResponse;
import tech.qizz.core.user.dto.UserResponse;

@RestController
@RequestMapping("/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    @GetMapping
//    public ResponseEntity<GetAllUserResponse> getUser() {
//        GetAllUserResponse getAllUserResponse = userService.getAllUser();
//        return new ResponseEntity<>(getAllUserResponse, HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}