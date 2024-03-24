package tech.qizz.core.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.qizz.core.entity.Question;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.QuizQuestion;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    List<QuizQuestion> findByQuiz(Quiz quiz);

    Optional<QuizQuestion> findByQuizAndQuestion(Quiz quiz, Question question);
}
