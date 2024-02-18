package tech.qizz.core.takeQuiz;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.QuizJoinedUser;
import tech.qizz.core.entity.User;

@Repository
public interface QuizJoinedUserRepository extends JpaRepository<QuizJoinedUser, Long> {

    boolean existsByQuizAndUser(Quiz quiz, User user);

    List<QuizJoinedUser> findAllByQuiz(Quiz quiz);

    Integer countByQuiz(Quiz quiz);

    void deleteByQuizAndUser(Quiz quiz, User user);
}
