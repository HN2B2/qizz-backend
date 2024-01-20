package tech.qizz.core.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.qizz.core.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { }
