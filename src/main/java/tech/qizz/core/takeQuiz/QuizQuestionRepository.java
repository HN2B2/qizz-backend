package tech.qizz.core.takeQuiz;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.qizz.core.entity.Question;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.QuizQuestion;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    Optional<QuizQuestion> findByQuizAndQuestion(Quiz quiz, Question question);
}
