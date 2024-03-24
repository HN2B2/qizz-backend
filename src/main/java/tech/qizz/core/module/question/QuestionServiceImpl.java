package tech.qizz.core.module.question;

import java.util.List;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import tech.qizz.core.repository.BankRepository;
import tech.qizz.core.entity.Question;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.entity.constant.QuestionType;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.module.question.dto.CreateQuestionRequest;
import tech.qizz.core.module.question.dto.QuestionResponse;
import tech.qizz.core.module.question.dto.UpdateQuestionRequest;
import tech.qizz.core.repository.QuestionRepository;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepository questionRepository;
    private BankRepository bankRepository;
    private final ModelMapper modelMapper;

    @Override
    public QuestionResponse getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Question not found"));

        return QuestionResponse.of(question);
    }

    @Override
    public QuestionResponse createQuestion(CreateQuestionRequest body) {
        Question question = Question.builder()
            .content(body.getContent())
            .point(body.getPoint())
            .duration(body.getDuration())
            .type(QuestionType.validateQuestionType(body.getType()))
            .answersMetadata(body.getAnswersMetadata())
            .correctAnswersMetadata(body.getCorrectAnswersMetadata())
            .explainAnswer(body.getExplainAnswer())
            .questionIndex(body.getQuestionIndex())
            .disabled(body.isDisabled())
            .quizBank(bankRepository.findById(body.getQuizBankId())
                .orElseThrow(() -> new NotFoundException("Bank not found")))
            .build();
        return QuestionResponse.of(questionRepository.save(question));
    }

    @Override
    public QuestionResponse updateQuestion(Long id, UpdateQuestionRequest body) {

        Question question = questionRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Question not found"));
        modelMapper.map(body, question);
        return QuestionResponse.of(questionRepository.save(question));
    }

    @Override
    public void deleteQuestion(Long id) {
        Question question = questionRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Question not found"));
        questionRepository.delete(question);
    }

    @Override
    public List<QuestionResponse> getAllQuestionsByBankId(Long bankId) {

        QuizBank quizBank = bankRepository.findById(bankId)
            .orElseThrow(() -> new NotFoundException("Bank not found"));
        return questionRepository.findAllByQuizBankAndDisabledFalse(quizBank).stream()
            .map(QuestionResponse::of).toList();
    }
}
