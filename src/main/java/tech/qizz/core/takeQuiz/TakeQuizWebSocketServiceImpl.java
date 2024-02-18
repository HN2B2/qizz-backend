package tech.qizz.core.takeQuiz;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.qizz.core.auth.jwt.JwtService;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.QuizJoinedUser;
import tech.qizz.core.entity.User;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.quiz.QuizRepository;
import tech.qizz.core.takeQuiz.dto.QuizRoomInfoResponse;
import tech.qizz.core.takeQuiz.dto.RoomUserResponse;

@Service
@RequiredArgsConstructor
public class TakeQuizWebSocketServiceImpl implements TakeQuizWebSocketService {

    private final JwtService jwtService;
    private final QuizRepository quizRepository;
    private final QuizJoinedUserRepository quizJoinedUserRepository;

    @Override
    public QuizRoomInfoResponse joinQuizRoom(String quizCode, String token) {
        Quiz quiz = quizRepository.findByCode(quizCode)
            .orElseThrow(() -> new NotFoundException("Quiz not found"));
        User user = jwtService.extractUser(token);
        if (
            !quizJoinedUserRepository.existsByQuizAndUser(quiz, user)
                && !quiz.isStarted()
                && !quiz.isEnded()
        ) {
            QuizJoinedUser quizJoinedUser = new QuizJoinedUser();
            quizJoinedUser.setQuiz(quiz);
            quizJoinedUser.setUser(user);
            quizJoinedUserRepository.save(quizJoinedUser);
        }
        Integer current = quizJoinedUserRepository.countByQuiz(quiz);
        List<User> users = quizJoinedUserRepository
            .findAllByQuiz(quiz)
            .stream()
            .map(QuizJoinedUser::getUser).toList();

        List<RoomUserResponse> roomUsers = users.stream().map(RoomUserResponse::of).toList();

        return new QuizRoomInfoResponse(
            quiz.getCode(),
            quiz.getName(),
            0,
            current,
            quiz.isStarted(),
            quiz.isEnded(),
            roomUsers
        );
    }

}
