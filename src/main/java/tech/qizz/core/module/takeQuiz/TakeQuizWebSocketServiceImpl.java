package tech.qizz.core.module.takeQuiz;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.Question;
import tech.qizz.core.entity.QuestionHistory;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.QuizJoinedUser;
import tech.qizz.core.entity.QuizQuestion;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.constant.QuizState;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.exception.ForbiddenException;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.module.auth.jwt.JwtService;
import tech.qizz.core.module.takeQuiz.dto.QuizRoomInfoResponse;
import tech.qizz.core.module.takeQuiz.dto.WebSocketRequest;
import tech.qizz.core.module.takeQuiz.dto.playing.AnswerRequest;
import tech.qizz.core.module.takeQuiz.dto.playing.PlayingQuestionResponse;
import tech.qizz.core.module.takeQuiz.dto.playing.PlayingResponse;
import tech.qizz.core.module.takeQuiz.dto.playing.PlayingState;
import tech.qizz.core.module.takeQuiz.dto.playing.RankingResponse;
import tech.qizz.core.module.takeQuiz.dto.playing.UserRankingResponse;
import tech.qizz.core.module.takeQuiz.dto.waitingRoom.JoinState;
import tech.qizz.core.module.takeQuiz.dto.waitingRoom.KickPlayerRequest;
import tech.qizz.core.module.takeQuiz.dto.waitingRoom.RoomUserResponse;
import tech.qizz.core.module.takeQuiz.dto.waitingRoom.WaitingRoomResponse;
import tech.qizz.core.repository.QuestionHistoryRepository;
import tech.qizz.core.repository.QuestionRepository;
import tech.qizz.core.repository.QuizJoinedUserRepository;
import tech.qizz.core.repository.QuizQuestionRepository;
import tech.qizz.core.repository.QuizRepository;
import tech.qizz.core.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class TakeQuizWebSocketServiceImpl implements TakeQuizWebSocketService {

    private final JwtService jwtService;
    private final QuizRepository quizRepository;
    private final QuizJoinedUserRepository quizJoinedUserRepository;
    private final QuestionHistoryRepository questionHistoryRepository;
    private final QuestionRepository questionRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate template;


    @Override
    public void answer(String quizCode, Long questionId, User user, AnswerRequest answer) {
        Quiz quiz = quizRepository.findByCode(quizCode)
            .orElseThrow(() -> new NotFoundException("Quiz not found"));
        QuizJoinedUser quizJoinedUser = quizJoinedUserRepository.findByQuizAndUser(quiz, user)
            .orElseThrow(() -> new NotFoundException("User not found"));
        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new NotFoundException("Question not found"));
        QuizQuestion quizQuestion = quizQuestionRepository.findByQuizAndQuestion(quiz, question)
            .orElseThrow(() -> new NotFoundException("Quiz question not found"));

        if (!quiz.getQuizState().equals(QuizState.STARTED) ||
            !quizQuestion.getAnswering() ||
            questionHistoryRepository.existsByQuizJoinedUserAndQuizQuestion(quizJoinedUser,
                quizQuestion)
        ) {
            throw new BadRequestException("Invalid answer");
        }

        boolean isCorrect = question.getCorrectAnswersMetadata().equals(answer.getAnswerMetadata());
        long score =
            isCorrect ? Math.round(answer.getAnswerTime() / question.getDuration() * 3000) : 0;
        questionHistoryRepository.save(
            QuestionHistory.builder()
                .answerMetadata(answer.getAnswerMetadata())
                .quizQuestion(quizQuestion)
                .quizJoinedUser(quizJoinedUser)
                .score(score)
                .answerTime(answer.getAnswerTime())
                .build()
        );
    }

    @Override
    public void joinQuizRoom(String quizCode, String token) {
        Quiz quiz = quizRepository.findByCode(quizCode)
            .orElseThrow(() -> new NotFoundException("Quiz not found"));
        User user = jwtService.extractUser(token);
        if (quiz.getQuizState().equals(QuizState.WAITING) &&
            !quizJoinedUserRepository.existsByQuizAndUser(quiz, user)
        ) {
            QuizJoinedUser quizJoinedUser = new QuizJoinedUser();
            quizJoinedUser.setQuiz(quiz);
            quizJoinedUser.setUser(user);
            quizJoinedUserRepository.save(quizJoinedUser);
        }

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

    @Override
    public RankingResponse getRanking(Quiz quiz, int current) {
        List<QuizJoinedUser> players = quizJoinedUserRepository.findAllByQuiz(quiz);
        List<UserRankingResponse> playerRankings = new ArrayList<>();
        players.forEach(player -> {
            List<QuestionHistory> questionHistories = questionHistoryRepository.findAllByQuizJoinedUser(
                player);
            int correct = 0;
            long score = 0;
            for (QuestionHistory questionHistory : questionHistories) {
                if (questionHistory.getScore() > 0) {
                    correct++;
                    score += questionHistory.getScore();
                }
            }
            playerRankings.add(UserRankingResponse
                .builder()
                .displayName(player.getUser().getDisplayName())
                .score(score)
                .correctCount(correct)
                .wrongCount(current - correct)
                .build()
            );
        });
        return RankingResponse
            .builder()
            .totalQuestion(quiz.getQuizQuestions().size())
            .currentQuestion(current)
            .players(playerRankings)
            .build();
    }


    @Override
    public void startQuiz(String quizCode, String token) throws InterruptedException {
        Quiz quiz = quizRepository.findByCode(quizCode)
            .orElseThrow(() -> new NotFoundException("Quiz not found"));
        User ownQuizUser = jwtService.extractUser(token);
        if (ownQuizUser.getUserId() != quiz.getCreatedBy().getUserId()) {
            throw new ForbiddenException("You are not the owner of this quiz");
        }
        quiz.setQuizState(QuizState.STARTED);
        quizRepository.save(quiz);
        PlayingResponse<Object> data = PlayingResponse
            .builder()
            .state(PlayingState.COUNTDOWN)
            .data("")
            .build();
        sendPlayingResponse(quiz, data);
        Thread.sleep(3000);

        AtomicInteger current = new AtomicInteger(1);
        quiz.getQuizQuestions().forEach((quizQuestion -> {
            quizQuestion.setAnswering(true);
            quizQuestionRepository.save(quizQuestion);
            sendPlayingResponse(quiz, PlayingResponse
                .builder()
                .state(PlayingState.ANSWERING)
                .data(PlayingQuestionResponse.of(quizQuestion.getQuestion(), false))
                .build());
            try {
                int time = quizQuestion.getQuestion().getDuration() * 1000;
                Thread.sleep(time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            quizQuestion.setAnswering(false);
            quizQuestionRepository.save(quizQuestion);
            sendPlayingResponse(quiz, PlayingResponse
                .builder()
                .state(PlayingState.RESULT)
                .data(PlayingQuestionResponse.of(quizQuestion.getQuestion(), true))
                .build());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sendPlayingResponse(quiz, PlayingResponse
                .builder()
                .state(PlayingState.RANKING)
                .data(getRanking(quiz, current.getAndIncrement()))
                .build());
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));
        quiz.setQuizState(QuizState.ENDED);
        quizRepository.save(quiz);
        template.convertAndSend("/play/" + quiz.getCode(), new QuizRoomInfoResponse<>(
            quiz.getCode(),
            quiz.getName(),
            quiz.getQuizState(),
            getRanking(quiz, quiz.getQuizQuestions().size())
        ));
    }

    @Override
    public QuizRoomInfoResponse<WaitingRoomResponse> checkMonitor(String quizCode, User user) {
        Quiz quiz = quizRepository.findByCode(quizCode)
            .orElseThrow(() -> new NotFoundException("Quiz not found"));
        if (quiz.getCreatedBy().getUserId() != user.getUserId()) {
            throw new ForbiddenException("You are not the owner of this quiz");
        }
        return getRoomInfo(quiz);
    }

    @Override
    public void kickPlayer(String quizCode, WebSocketRequest<KickPlayerRequest> body) {
        Quiz quiz = quizRepository.findByCode(quizCode)
            .orElseThrow(() -> new NotFoundException("Quiz not found"));
        User ownQuizUser = jwtService.extractUser(body.getToken());
        if (ownQuizUser.getUserId() != quiz.getCreatedBy().getUserId()) {
            throw new ForbiddenException("You are not the owner of this quiz");
        }
        if (quiz.getQuizState().equals(QuizState.WAITING)) {
            throw new BadRequestException("Quiz is not waiting");
        }
        User user = userRepository.findByEmail(body.getData().getEmail())
            .orElseThrow(() -> new NotFoundException("User not found"));
        quizJoinedUserRepository.deleteByQuizAndUser(quiz, user);
        sendRoomInfo(quiz);
    }

    private void sendPlayingResponse(Quiz quiz,
        PlayingResponse<Object> playingResponse
    ) {
        QuizRoomInfoResponse<PlayingResponse<Object>> response = new QuizRoomInfoResponse<>(
            quiz.getCode(),
            quiz.getName(),
            quiz.getQuizState(),
            playingResponse
        );
        template.convertAndSend("/play/" + quiz.getCode(), response);
    }

}
