package tech.qizz.core.module.takeQuiz;

import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.User;
import tech.qizz.core.module.takeQuiz.dto.QuizRoomInfoResponse;
import tech.qizz.core.module.takeQuiz.dto.playing.AnswerRequest;
import tech.qizz.core.module.takeQuiz.dto.playing.RankingResponse;
import tech.qizz.core.module.takeQuiz.dto.waitingRoom.WaitingRoomResponse;

public interface TakeQuizWebSocketService {


    void answer(String quizCode, Long questionId, User user, AnswerRequest answer);

    void joinQuizRoom(String quizCode, String token);

    RankingResponse getRanking(Quiz quiz, int current);

    void startQuiz(String quizCode, String token) throws InterruptedException;

    QuizRoomInfoResponse<WaitingRoomResponse> checkMonitor(String quizCode, User user);

    void sendRoomInfo(Quiz quiz);
}
