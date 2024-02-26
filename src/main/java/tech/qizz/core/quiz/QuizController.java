package tech.qizz.core.quiz;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.quiz.dto.CreateQuizRequest;
import tech.qizz.core.quiz.dto.QuizResponse;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    @GetMapping("code/{quiz_code}")
    public ResponseEntity<QuizResponse> getQuizByQuizCode(@PathVariable("quiz_code") String quizCode) {
        return new ResponseEntity<>(quizService.getQuizByCode(quizCode), HttpStatus.OK);
    }

    @GetMapping("id/{quiz_id}")
    public ResponseEntity<QuizResponse> getQuizByQuizID(@PathVariable("quiz_id")  Long quizId) {
        return new ResponseEntity<>(quizService.getQuizById(quizId), HttpStatus.OK);
    }


    @PostMapping("bankId/{bankId}/createdBy/{createdBy}")
    @PreAuthorize("hasAnyAuthority('USER', 'STAFF','ADMIN')")
    public ResponseEntity<QuizResponse> createQuiz(@PathVariable Long bankId, @PathVariable Long createdBy,
                                                   @Valid @RequestBody CreateQuizRequest body, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException("Invalid request");
        }
        QuizResponse quiz = quizService.createQuiz(bankId,createdBy,body);
        return new ResponseEntity<>(quiz, HttpStatus.CREATED);
    }
}
