package tech.qizz.core.takeQuiz;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import tech.qizz.core.takeQuiz.dto.UserJoinRoomResponse;

@Controller
public class TakeQuizController {

    @MessageMapping("/join/{quizCode}")
    @SendTo("/play/{quizCode}")
    public ResponseEntity<UserJoinRoomResponse> takeQuiz() {
        return new ResponseEntity<UserJoinRoomResponse>(
            UserJoinRoomResponse.builder().username("test").avatar("test").displayName("test")
                .build()
            , HttpStatus.OK);
    }
}
