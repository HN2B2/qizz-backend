package tech.qizz.core.module.takeQuiz;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.qizz.core.annotation.RequestUser;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.User;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.module.takeQuiz.dto.QuizRoomInfoResponse;
import tech.qizz.core.module.takeQuiz.dto.playing.AnswerRequest;
import tech.qizz.core.module.takeQuiz.dto.playing.RankingResponse;
import tech.qizz.core.module.takeQuiz.dto.waitingRoom.WaitingRoomResponse;
import tech.qizz.core.repository.QuizRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/take-quiz")
public class TakeQuizController {

    private final TakeQuizWebSocketService takeQuizWebSocketService;
    private final QuizRepository quizRepository;

    @GetMapping("/result/{quizCode}")
    public ResponseEntity<QuizRoomInfoResponse<RankingResponse>> getRanking(
        @PathVariable String quizCode
    ) {
        Quiz quiz = quizRepository.findByCode(quizCode)
            .orElseThrow(() -> new NotFoundException("Quiz not found"));
        RankingResponse ranking = takeQuizWebSocketService.getRanking(quiz,
            quiz.getQuizQuestions().size());
        return new ResponseEntity<>(
            QuizRoomInfoResponse.<RankingResponse>builder()
                .quizCode(quizCode)
                .quizName(quiz.getName())
                .state(quiz.getQuizState())
                .data(ranking)
                .build(),
            HttpStatus.OK
        );
    }

    @PostMapping("/{quizCode}/{questionId}")
    public ResponseEntity<Object> answerQuestion(
        @PathVariable String quizCode,
        @PathVariable Long questionId,
        @RequestUser User user,
        @RequestBody AnswerRequest answer
    ) {
        takeQuizWebSocketService.answer(quizCode, questionId, user, answer);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GetMapping("/monitor/{quizCode}")
    @PreAuthorize("hasAnyAuthority('USER', 'STAFF', 'ADMIN')")
    public QuizRoomInfoResponse<WaitingRoomResponse> monitor(
        @PathVariable String quizCode,
        @RequestUser User user
    ) {
        return takeQuizWebSocketService.checkMonitor(quizCode, user);
    }
}
