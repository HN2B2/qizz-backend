package tech.qizz.core.quiz;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.qizz.core.quiz.dto.QuizResponse;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    @GetMapping("{quizCode}")
    public ResponseEntity<QuizResponse> getQuiz(@PathVariable String quizCode) {
        return new ResponseEntity<>(quizService.getQuizByCode(quizCode), HttpStatus.OK);
    }
}
