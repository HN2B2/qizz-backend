package tech.qizz.core.quiz;

import java.util.Date;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.User;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Optional<Quiz> findByCode(String code);

    Page<Quiz> findAllByNameContainingAndCreatedByAndCreatedAtBetween(
        String name,
        User createdBy,
        Date from,
        Date to,
        Pageable pageable
    );
    boolean existsByCode(String code);
}
