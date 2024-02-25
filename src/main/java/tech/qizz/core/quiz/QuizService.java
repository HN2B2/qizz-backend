package tech.qizz.core.quiz;

import tech.qizz.core.entity.Quiz;
import tech.qizz.core.quiz.dto.QuizResponse;

public interface QuizService {

    public QuizResponse getQuizByCode(String quizCode);
}
