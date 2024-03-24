package tech.qizz.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.qizz.core.entity.QuestionHistory;
import tech.qizz.core.entity.QuizJoinedUser;
import tech.qizz.core.entity.QuizQuestion;

@Repository
public interface QuestionHistoryRepository extends JpaRepository<QuestionHistory, Long> {

    boolean existsByQuizJoinedUserAndQuizQuestion(QuizJoinedUser user, QuizQuestion question);

    List<QuestionHistory> findAllByQuizJoinedUser(QuizJoinedUser user);
}
