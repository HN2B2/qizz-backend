package tech.qizz.core.question;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.question.dto.CreateQuestionRequest;
import tech.qizz.core.question.dto.QuestionResponse;
import tech.qizz.core.question.dto.UpdateQuestionRequest;

import java.util.List;

@RestController
@Controller
@AllArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    private QuestionService questionService;

    @GetMapping("/all/bankId/{bankId}")
    public ResponseEntity<List<QuestionResponse>> getAllQuestionsByBankId(@PathVariable Long bankId) {
        List<QuestionResponse> questions = questionService.getAllQuestionsByBankId(bankId);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long id) {
        QuestionResponse question = questionService.getQuestionById(id);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(@Valid @RequestBody CreateQuestionRequest body, BindingResult result) {
        if (result.hasErrors() || body == null) {
            throw new BadRequestException("Invalid request");
        }
        System.out.println(body);
        QuestionResponse question = questionService.createQuestion(body);
        return new ResponseEntity<>(question, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable Long id, @Valid @RequestBody UpdateQuestionRequest body, BindingResult result) {

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
