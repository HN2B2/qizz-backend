package tech.qizz.core.quiz;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.quiz.dto.QuizResponse;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    @Override
    public QuizResponse getQuizByCode(String quizCode) {
        Quiz quiz = quizRepository.findByCode(quizCode).orElseThrow(
            () -> new NotFoundException("Quiz with not found")
        );
        return QuizResponse.of(quiz);
    }
}
