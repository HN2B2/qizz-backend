package tech.qizz.core.takeQuiz;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import tech.qizz.core.auth.jwt.JwtService;
import tech.qizz.core.entity.Question;
import tech.qizz.core.entity.QuestionHistory;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.QuizJoinedUser;
import tech.qizz.core.entity.QuizQuestion;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.constant.QuizState;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.question.QuestionRepository;
import tech.qizz.core.quiz.QuizRepository;
import tech.qizz.core.takeQuiz.dto.QuizRoomInfoResponse;
import tech.qizz.core.takeQuiz.dto.playing.AnswerRequest;
import tech.qizz.core.takeQuiz.dto.playing.PlayingQuestionResponse;
import tech.qizz.core.takeQuiz.dto.playing.PlayingResponse;
import tech.qizz.core.takeQuiz.dto.playing.PlayingState;
import tech.qizz.core.takeQuiz.dto.playing.RankingResponse;
import tech.qizz.core.takeQuiz.dto.playing.UserRankingResponse;
import tech.qizz.core.takeQuiz.dto.waitingRoom.JoinState;
import tech.qizz.core.takeQuiz.dto.waitingRoom.RoomUserResponse;
import tech.qizz.core.takeQuiz.dto.waitingRoom.WaitingRoomResponse;

@Service
@RequiredArgsConstructor
public class TakeQuizWebSocketServiceImpl implements TakeQuizWebSocketService {

    private final JwtService jwtService;
    private final QuizRepository quizRepository;
    private final QuizJoinedUserRepository quizJoinedUserRepository;
    private final QuestionHistoryRepository questionHistoryRepository;
    private final QuestionRepository questionRepository;
    private final QuizQuestionRepository quizQuestionRepository;
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
    public QuizRoomInfoResponse<WaitingRoomResponse> joinQuizRoom(String quizCode, String token) {
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
    public void startQuiz(String quizCode) throws InterruptedException {
        Quiz quiz = quizRepository.findByCode(quizCode)
            .orElseThrow(() -> new NotFoundException("Quiz not found"));
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
