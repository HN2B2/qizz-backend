package tech.qizz.core.module.takeQuiz;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.qizz.core.module.takeQuiz.dto.QuizRoomInfoResponse;
import tech.qizz.core.module.takeQuiz.dto.WebSocketRequest;
import tech.qizz.core.module.takeQuiz.dto.waitingRoom.WaitingRoomResponse;

@Controller
@CrossOrigin
@RequiredArgsConstructor
public class TakeQuizWebSocketController {

    private final SimpMessagingTemplate template;
    private final TakeQuizWebSocketService takeQuizWebSocketService;

    @MessageMapping("/join/{quizCode}")
    public void joinQuizRoom(
        @DestinationVariable String quizCode,
        WebSocketRequest<String> body
    ) {
        QuizRoomInfoResponse<WaitingRoomResponse> waitingRoomInfo = takeQuizWebSocketService.joinQuizRoom(
            quizCode,
            body.getToken());
        template.convertAndSend("/play/" + quizCode, waitingRoomInfo);
    }

    @MessageMapping("/start/{quizCode}")
    public void startQuiz(
        @DestinationVariable String quizCode
    ) {
        try {
            takeQuizWebSocketService.startQuiz(quizCode);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
