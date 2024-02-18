package tech.qizz.core.takeQuiz;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.qizz.core.takeQuiz.dto.QuizRoomInfoResponse;
import tech.qizz.core.takeQuiz.dto.WebSocketRequest;

@Controller
@CrossOrigin
@RequiredArgsConstructor
public class TakeQuizWebSocketController {

    private final SimpMessagingTemplate template;
    private final TakeQuizWebSocketService takeQuizWebSocketService;

    @MessageMapping("/join/{quizCode}")
    public QuizRoomInfoResponse joinQuizRoom(
        @DestinationVariable String quizCode,
        WebSocketRequest<String> body
    ) {
        QuizRoomInfoResponse roomInfo = takeQuizWebSocketService.joinQuizRoom(quizCode,
            body.getToken());
        template.convertAndSend("/play/" + quizCode, roomInfo);
        return roomInfo;
    }
}
