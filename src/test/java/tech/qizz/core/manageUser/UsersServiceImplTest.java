package tech.qizz.core.manageUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.constant.UserRole;
import tech.qizz.core.repository.UserRepository;
import tech.qizz.core.module.manageUser.UsersServiceImpl;
import tech.qizz.core.module.manageUser.dto.GetAllUserResponse;
import tech.qizz.core.module.manageUser.dto.UpdateUserRequest;
import tech.qizz.core.module.manageUser.dto.UsersResponse;

@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {

    @InjectMocks
    private UsersServiceImpl usersService;

    @Mock
    private UserRepository userRepository;

    @Test
    void testGetAllUser() {
        // Mock data
        Page<User> mockedUsersPage = new PageImpl<>(Arrays.asList(new User(), new User()));

        // Mock repository method
        when(userRepository.findUsersByKeywordAndRoleAndBanned(any(), any(), any(), any()))
            .thenReturn(mockedUsersPage);

        // Call the service method
        GetAllUserResponse response = usersService.getAllUser(1, 10, "keyword", UserRole.ADMIN,
            false, "id", "desc");

        // Assertions
        assertEquals(2, response.getData().size());
    }

    @Test
    void testGetUserById() {
        // Mock data
        Long userId = 1L;
        User mockedUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockedUser));

        // Call the service method
        UsersResponse response = usersService.getUserById(userId);

        // Assertions
        assertEquals(UsersResponse.of(mockedUser), response);
    }

    @Test
    void testUpdateUser() {
        // Mock data
        Long userId = 1L;
        User mockedUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockedUser));

        // Call the service method
        UsersResponse response = usersService.updateUser(userId, new UpdateUserRequest());

        // Assertions
        assertEquals(UsersResponse.of(mockedUser), response);
    }

}
