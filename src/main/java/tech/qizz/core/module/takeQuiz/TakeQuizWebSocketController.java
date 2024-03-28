package tech.qizz.core.module.takeQuiz;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import tech.qizz.core.module.takeQuiz.dto.WebSocketRequest;
import tech.qizz.core.module.takeQuiz.dto.waitingRoom.KickPlayerRequest;

@Controller
@RequiredArgsConstructor
public class TakeQuizWebSocketController {

    private final TakeQuizWebSocketService takeQuizWebSocketService;

    @MessageMapping("/join/{quizCode}")
    public void joinQuizRoom(
        @DestinationVariable String quizCode,
        WebSocketRequest<String> body
    ) {
        takeQuizWebSocketService.joinQuizRoom(
            quizCode,
            body.getToken());
    }

    @MessageMapping("/start/{quizCode}")
    public void startQuiz(
        @DestinationVariable String quizCode,
        WebSocketRequest<String> body
    ) {
        try {
            takeQuizWebSocketService.startQuiz(quizCode, body.getToken());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @MessageMapping("/kick/{quizCode}")
    public void kickPlayer(
        @DestinationVariable String quizCode,
        WebSocketRequest<KickPlayerRequest> body
    ) {
        try {
            takeQuizWebSocketService.kickPlayer(quizCode, body);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
