package tech.qizz.core.takeQuiz;

import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.User;
import tech.qizz.core.takeQuiz.dto.QuizRoomInfoResponse;
import tech.qizz.core.takeQuiz.dto.playing.AnswerRequest;
import tech.qizz.core.takeQuiz.dto.playing.RankingResponse;
import tech.qizz.core.takeQuiz.dto.waitingRoom.WaitingRoomResponse;

public interface TakeQuizWebSocketService {


    void answer(String quizCode, Long questionId, User user, AnswerRequest answer);

    QuizRoomInfoResponse<WaitingRoomResponse> joinQuizRoom(String quizCode, String token);

    RankingResponse getRanking(Quiz quiz, int current);

    void startQuiz(String quizCode) throws InterruptedException;
}
