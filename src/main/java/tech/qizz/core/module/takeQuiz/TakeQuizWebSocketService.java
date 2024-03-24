package tech.qizz.core.module.takeQuiz;

import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.User;
import tech.qizz.core.module.takeQuiz.dto.playing.RankingResponse;
import tech.qizz.core.module.takeQuiz.dto.waitingRoom.WaitingRoomResponse;
import tech.qizz.core.module.takeQuiz.dto.QuizRoomInfoResponse;
import tech.qizz.core.module.takeQuiz.dto.playing.AnswerRequest;

public interface TakeQuizWebSocketService {


    void answer(String quizCode, Long questionId, User user, AnswerRequest answer);

    QuizRoomInfoResponse<WaitingRoomResponse> joinQuizRoom(String quizCode, String token);

    RankingResponse getRanking(Quiz quiz, int current);

    void startQuiz(String quizCode) throws InterruptedException;
}
