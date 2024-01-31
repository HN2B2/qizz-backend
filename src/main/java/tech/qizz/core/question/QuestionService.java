package tech.qizz.core.question;

import tech.qizz.core.question.dto.CreateQuestionRequest;
import tech.qizz.core.question.dto.QuestionResponse;
import tech.qizz.core.question.dto.UpdateQuestionRequest;

import java.util.List;

public interface QuestionService {
    public QuestionResponse getQuestionById(Long id);
    public QuestionResponse createQuestion(CreateQuestionRequest body);

    public QuestionResponse updateQuestion(Long id, UpdateQuestionRequest body);

    public void deleteQuestion(Long id);

    public List<QuestionResponse> getAllQuestionsByBankId(Long bankId);
}
