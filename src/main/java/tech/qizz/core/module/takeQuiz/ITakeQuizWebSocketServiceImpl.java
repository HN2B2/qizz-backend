package tech.qizz.core.module.takeQuiz;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.QuizJoinedUser;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.constant.QuizState;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.exception.ForbiddenException;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.module.auth.jwt.JwtService;
import tech.qizz.core.module.takeQuiz.dto.QuizRoomInfoResponse;
import tech.qizz.core.module.takeQuiz.dto.WebSocketRequest;
import tech.qizz.core.module.takeQuiz.dto.waitingRoom.JoinState;
import tech.qizz.core.module.takeQuiz.dto.waitingRoom.KickPlayerRequest;
import tech.qizz.core.module.takeQuiz.dto.waitingRoom.RoomUserResponse;
import tech.qizz.core.module.takeQuiz.dto.waitingRoom.WaitingRoomResponse;
import tech.qizz.core.repository.QuizJoinedUserRepository;
import tech.qizz.core.repository.QuizRepository;
import tech.qizz.core.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class ITakeQuizWebSocketServiceImpl implements ITakeQuizWebSocketService {

    private final QuizRepository quizRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final QuizJoinedUserRepository quizJoinedUserRepository;
    private final SimpMessagingTemplate template;

    @Override
    public void kickPlayer(String quizCode, WebSocketRequest<KickPlayerRequest> body) {
        Quiz quiz = quizRepository.findByCode(quizCode)
            .orElseThrow(() -> new NotFoundException("Quiz not found"));
        User ownQuizUser = jwtService.extractUser(body.getToken());
        if (ownQuizUser.getUserId() != quiz.getCreatedBy().getUserId()) {
            throw new ForbiddenException("You are not the owner of this quiz");
        }
        if (!quiz.getQuizState().equals(QuizState.WAITING)) {
            throw new BadRequestException("Quiz is not waiting");
        }
        User user = userRepository.findByEmail(body.getData().getEmail())
            .orElseThrow(() -> new NotFoundException("User not found"));
        quizJoinedUserRepository.removeByQuizAndUser(quiz, user);
        sendRoomInfo(quiz);
    }

    private void sendRoomInfo(Quiz quiz) {
        template.convertAndSend("/play/" + quiz.getCode(), getRoomInfo(quiz));
    }

    private QuizRoomInfoResponse<WaitingRoomResponse> getRoomInfo(Quiz quiz) {
        Integer current = quizJoinedUserRepository.countByQuiz(quiz);
        List<User> users = quizJoinedUserRepository
            .findAllByQuiz(quiz)
            .stream()
            .map(QuizJoinedUser::getUser).toList();

        List<RoomUserResponse> roomUsers = users.stream().map(RoomUserResponse::of).toList();

        WaitingRoomResponse data = WaitingRoomResponse
            .builder()
            .joinSate(JoinState.SUCCESS)
            .message("Success")
            .total(0)
            .current(current)
            .users(roomUsers)
            .build();
        return new QuizRoomInfoResponse<>(
            quiz.getCode(),
            quiz.getName(),
            quiz.getQuizState(),
            data
        );
    }
}
