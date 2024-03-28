package tech.qizz.core.module.question;

import tech.qizz.core.module.question.dto.CreateQuestionRequest;
import tech.qizz.core.module.question.dto.QuestionResponse;
import tech.qizz.core.module.question.dto.UpdateQuestionRequest;

import java.util.List;

public interface QuestionService {
    public QuestionResponse getQuestionById(Long id);
    public QuestionResponse createQuestion(CreateQuestionRequest body);

    public QuestionResponse updateQuestion(Long id, UpdateQuestionRequest body);

    public void deleteQuestion(Long id);

    public List<QuestionResponse> getAllQuestionsByBankId(Long bankId);
}
