package tech.qizz.core.manageUser;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.constant.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE " +
        "(u.username LIKE CONCAT('%', :keyword, '%') OR " +
        "u.email LIKE CONCAT('%', :keyword, '%') OR " +
        "u.displayName LIKE CONCAT('%', :keyword, '%')) AND " +
        "(:role IS NULL OR u.role = :role) AND " +
            "(u.role!='GUEST') AND "+
        "(:banned IS NULL OR u.banned = :banned)"
    )
    Page<User> findUsersByKeywordAndRoleAndBanned(
        @Param("keyword") String keyword,
        @Param("role") UserRole role,
        @Param("banned") Boolean banned,
        Pageable pageable
    );

    @Query("SELECT u FROM User u WHERE " +
            "(u.email LIKE CONCAT('%', :keyword, '%'))"
    )
    Page<User> findUserEmailsByKeyword(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    Optional<User> findByEmail(String email);

    boolean existsByUsernameOrEmail(String username, String email);

    boolean existsByRole(UserRole role);
}
