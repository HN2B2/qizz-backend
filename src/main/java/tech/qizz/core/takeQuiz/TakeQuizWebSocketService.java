package tech.qizz.core.takeQuiz;

import tech.qizz.core.takeQuiz.dto.QuizRoomInfoResponse;

public interface TakeQuizWebSocketService {

    QuizRoomInfoResponse joinQuizRoom(String quizCode, String token);

}
