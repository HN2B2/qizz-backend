package tech.qizz.core.module.quiz;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tech.qizz.core.annotation.RequestUser;
import tech.qizz.core.entity.User;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.module.quiz.dto.CreateQuizRequest;
import tech.qizz.core.module.quiz.dto.QuizResponse;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'STAFF', 'ADMIN')")
    public ResponseEntity<QuizResponse> getQuizByQuizCode(@RequestParam String code) {
        return new ResponseEntity<>(quizService.getQuizByCode(code), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'STAFF', 'ADMIN')")
    public ResponseEntity<QuizResponse> getQuizByQuizID(@PathVariable Long id) {
        return new ResponseEntity<>(quizService.getQuizById(id), HttpStatus.OK);
    }


    @PostMapping("bankId/{bankId}")
    @PreAuthorize("hasAnyAuthority('USER', 'STAFF','ADMIN')")
    public ResponseEntity<QuizResponse> createQuiz(@PathVariable Long bankId, @RequestUser User user,
                                                   @Valid @RequestBody CreateQuizRequest body, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException("Invalid request");
        }
        QuizResponse quiz = quizService.createQuiz(bankId,user.getUserId(),body);
        return new ResponseEntity<>(quiz, HttpStatus.CREATED);
    }
}
