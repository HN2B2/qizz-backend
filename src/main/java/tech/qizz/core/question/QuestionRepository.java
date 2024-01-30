package tech.qizz.core.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.qizz.core.entity.Question;
import tech.qizz.core.entity.QuizBank;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    public List<Question> findAllByQuizBank(QuizBank quizBank);
}
