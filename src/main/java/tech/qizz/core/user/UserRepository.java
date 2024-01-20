package tech.qizz.core.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.qizz.core.entity.User;
import tech.qizz.core.user.constant.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE " +
        "u.username LIKE CONCAT('%', :keyword, '%') OR " +
        "u.email LIKE CONCAT('%', :keyword, '%') OR " +
        "u.displayName LIKE CONCAT('%', :keyword, '%') AND " +
        "u.role = :role AND " +
        "(:banned IS NULL OR u.banned = :banned)"
    )
    Page<User> findUsersByKeywordAndRoleAndBanned(
        @Param("keyword") String keyword,
        @Param("role") UserRole role,
        @Param("banned") Boolean banned,
        Pageable pageable
    );


}
