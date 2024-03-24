package tech.qizz.core.module.quiz;

import tech.qizz.core.module.quiz.dto.CreateQuizRequest;
import tech.qizz.core.module.quiz.dto.QuizResponse;

public interface QuizService {

    public QuizResponse getQuizByCode(String quizCode);

public QuizResponse getQuizById(Long quizId);

    public QuizResponse createQuiz(Long bankId, Long createdBy, CreateQuizRequest body);
}
