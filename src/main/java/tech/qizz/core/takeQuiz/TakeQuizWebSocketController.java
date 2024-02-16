package tech.qizz.core.takeQuiz;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.qizz.core.takeQuiz.dto.UserJoinRoomResponse;

@Controller
@CrossOrigin
@RequiredArgsConstructor
public class TakeQuizWebSocketController {

    private final SimpMessagingTemplate template;

    @MessageMapping("/join/{quizCode}")
    public UserJoinRoomResponse takeQuiz(@DestinationVariable String quizCode) {
        UserJoinRoomResponse user = UserJoinRoomResponse
            .builder()
            .username("test")
            .avatar("test")
            .displayName("test")
            .build();
        template.convertAndSend("/play/" + quizCode, user);
        return user;
    }
}
