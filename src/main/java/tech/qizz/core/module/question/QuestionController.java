package tech.qizz.core.module.question;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.module.question.dto.CreateQuestionRequest;
import tech.qizz.core.module.question.dto.QuestionResponse;
import tech.qizz.core.module.question.dto.UpdateQuestionRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/question")
public class QuestionController {

    private QuestionService questionService;

    @GetMapping("/all/bankId/{bankId}")
    public ResponseEntity<List<QuestionResponse>> getAllQuestionsByBankId(
        @PathVariable Long bankId) {
        List<QuestionResponse> questions = questionService.getAllQuestionsByBankId(bankId);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long id) {
        QuestionResponse question = questionService.getQuestionById(id);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(
        @Valid @RequestBody CreateQuestionRequest body, BindingResult result) {
        if (result.hasErrors() || body == null) {
            throw new BadRequestException("Invalid request");
        }
        System.out.println(body);
        QuestionResponse question = questionService.createQuestion(body);
        return new ResponseEntity<>(question, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable Long id,
        @Valid @RequestBody UpdateQuestionRequest body, BindingResult result) {

        if (result.hasErrors() || body == null) {
            throw new BadRequestException("Invalid request");
        }
        QuestionResponse question = questionService.updateQuestion(id, body);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
