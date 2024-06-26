package tech.qizz.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.qizz.core.entity.Question;
import tech.qizz.core.entity.QuizBank;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    public List<Question> findAllByQuizBankAndDisabledFalse(QuizBank quizBank);
    //I want list question by quiz bank but question.disabled=false


}
