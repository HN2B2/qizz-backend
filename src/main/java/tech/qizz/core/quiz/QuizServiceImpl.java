package tech.qizz.core.quiz;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import tech.qizz.core.bank.BankRepository;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.constant.QuizState;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.manageUser.UserRepository;
import tech.qizz.core.manageUser.dto.UsersResponse;
import tech.qizz.core.quiz.dto.CreateQuizRequest;
import tech.qizz.core.quiz.dto.QuizResponse;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final BankRepository bankRepository;
    private final UserRepository userRepository;


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

        QuizBank quizBankId = bankRepository.findById(bankId)
                .orElseThrow(() -> new NotFoundException("Bank not found"));
        User createrBy = userRepository.findById(createdBy)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Quiz quiz = Quiz.builder()
                .code(randomNumeric)
                .name(body.getQuizName())
                .description(body.getDescription())
                .featuredImage(body.getFeaturedImage())
                .quizBank(quizBankId)
                .createdBy(createrBy)
                .quizState(QuizState.WAITING)
                .build();
        return QuizResponse.of(quizRepository.save(quiz));
    }


}
