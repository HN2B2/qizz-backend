package tech.qizz.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.QuizJoinedUser;
import tech.qizz.core.entity.User;

@Repository
public interface QuizJoinedUserRepository extends JpaRepository<QuizJoinedUser, Long> {

    Optional<QuizJoinedUser> findByQuizAndUser(Quiz quiz, User user);

    boolean existsByQuizAndUser(Quiz quiz, User user);

    List<QuizJoinedUser> findAllByQuiz(Quiz quiz);

    Integer countByQuiz(Quiz quiz);

    void deleteByQuizAndUser(Quiz quiz, User user);
}
