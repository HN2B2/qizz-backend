package tech.qizz.core.module.quiz;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.*;
import tech.qizz.core.repository.BankRepository;
import tech.qizz.core.entity.constant.QuizState;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.repository.QuizQuestionRepository;
import tech.qizz.core.repository.QuizRepository;
import tech.qizz.core.repository.UserRepository;
import tech.qizz.core.module.quiz.dto.CreateQuizRequest;
import tech.qizz.core.module.quiz.dto.QuizResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final BankRepository bankRepository;
    private final UserRepository userRepository;
    private final QuizQuestionRepository quizQuestionRepository;



    @Override
    public QuizResponse getQuizById(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(
            () -> new NotFoundException("Quiz with not found")
        );
        return QuizResponse.of(quiz);
    }

    @Override
    public QuizResponse getQuizByCode(String quizCode) {
        Quiz quiz = quizRepository.findByCode(quizCode).orElseThrow(
                () -> new NotFoundException("Quiz with not found")
        );
        return QuizResponse.of(quiz);
    }

    @Override
    public QuizResponse createQuiz(Long bankId,Long createdBy, CreateQuizRequest body) {
        String randomNumeric = "";
        boolean exists = false;
       do{
           randomNumeric = RandomStringUtils.randomNumeric(8);
           exists = quizRepository.existsByCode(randomNumeric);
       }while(exists);

        QuizBank quizBank = bankRepository.findById(bankId)
                .orElseThrow(() -> new NotFoundException("Bank not found"));
        User createrBy = userRepository.findById(createdBy)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Quiz quiz = Quiz.builder()
                .code(randomNumeric)
                .name(body.getQuizName())
                .description(body.getDescription())
                .featuredImage(body.getFeaturedImage())
                .quizBank(quizBank)
                .createdBy(createrBy)
                .quizState(QuizState.WAITING)
                .build();

        quiz = quizRepository.save(quiz);
        List<Question> originalQuestions = quizBank.getQuestions();
        List<QuizQuestion> newQuestions = new ArrayList<>();

        for (Question originalQuestion : originalQuestions) {
            QuizQuestion newQuestion = new QuizQuestion();
            newQuestion.setQuiz(quiz);
            newQuestion.setAnswering(false);
            newQuestion.setQuestion(originalQuestion);
            newQuestions.add(newQuestion);
        }
        newQuestions = quizQuestionRepository.saveAll(newQuestions);
        quiz.setQuizQuestions(newQuestions);

        return QuizResponse.of(quizRepository.save(quiz));
    }


}
