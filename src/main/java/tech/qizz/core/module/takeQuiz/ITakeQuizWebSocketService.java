package tech.qizz.core.module.takeQuiz;

import tech.qizz.core.module.takeQuiz.dto.WebSocketRequest;
import tech.qizz.core.module.takeQuiz.dto.waitingRoom.KickPlayerRequest;

public interface ITakeQuizWebSocketService {

    void kickPlayer(String quizCode, WebSocketRequest<KickPlayerRequest> body);
}
