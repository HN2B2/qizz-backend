package tech.qizz.core.quiz;

import tech.qizz.core.quiz.dto.CreateQuizRequest;
import tech.qizz.core.quiz.dto.QuizResponse;

public interface QuizService {

    public QuizResponse getQuizByCode(String quizCode);

public QuizResponse getQuizById(Long quizId);

    public QuizResponse createQuiz(Long bankId, Long createdBy, CreateQuizRequest body);
}
