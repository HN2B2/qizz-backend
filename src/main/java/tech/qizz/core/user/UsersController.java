package tech.qizz.core.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tech.qizz.core.entity.constant.UserRole;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.user.dto.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    public ResponseEntity<GetAllUserResponse> getAllUser(
        @RequestParam(required = false, defaultValue = "1") Integer page,
        @RequestParam(required = false, defaultValue = "10") Integer limit,
        @RequestParam(required = false, defaultValue = "") String keyword,
        @RequestParam(required = false) String role,
        @RequestParam(required = false) Boolean banned,
        @RequestParam(required = false, defaultValue = "id") String order,
        @RequestParam(required = false, defaultValue = "desc") String sort
    ) {
        UserRole userRole = UserRole.validateUserRole(role);
        GetAllUserResponse user = usersService.getAllUser(
            page,
            limit,
            keyword,
            userRole,
            banned,
            order,
            sort
        );
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'STAFF', 'ADMIN')")
    public ResponseEntity<UsersResponse> getUserById(@PathVariable Long id) {
        UsersResponse user = usersService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UsersResponse> createUser(@Valid @RequestBody CreateUserRequest body,
        BindingResult result) {
        if (result.hasErrors() || body == null) {
            throw new BadRequestException("Invalid request");
        }
        UsersResponse user = usersService.createUser(body);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UsersResponse> updateUser(@PathVariable Long id,
        @Valid @RequestBody UpdateUserRequest body, BindingResult result) {
        if (result.hasErrors() || body == null) {
            throw new BadRequestException("Invalid request");
        }
        UsersResponse user = usersService.updateUser(id, body);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
//        usersService.deleteUser(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}