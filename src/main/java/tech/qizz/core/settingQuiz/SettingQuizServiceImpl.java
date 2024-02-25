package tech.qizz.core.settingQuiz;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.QuizSetting;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.UserMetadata;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.manageUser.UserRepository;
import tech.qizz.core.quiz.QuizRepository;
import tech.qizz.core.settingQuiz.dto.SettingQuizMetadataRequest;
import tech.qizz.core.settingQuiz.dto.SettingQuizRequest;
import tech.qizz.core.settingQuiz.dto.SettingQuizRespone;
import tech.qizz.core.user.dto.ProfileResponse;
import tech.qizz.core.user.dto.UpsertProfileMetadataRequest;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SettingQuizServiceImpl implements SettingQuizService {
    private final QuizRepository quizRepository;


    private static final Map<String, String> KEY_REGEX_MAP = Map.ofEntries(
            Map.entry("setTime","^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}Z$"),
            Map.entry("attempt","^[^\\/:*?\\\"<>|]+$"),
            Map.entry("showAnswersDuringAct","^(true|false)$"),
            Map.entry("showAnswersAfterAct","^(true|false)$"),
            Map.entry("shuffleQuestions","^(true|false)$"),
            Map.entry("shuffleAnswers","^(true|false)$"),
            Map.entry("questionTimer","^(defaultTimer|off)$"),
            Map.entry("skipQuestion","^(true|false)$"),
            Map.entry("powerUp","^(true|false)$"),
            Map.entry( "reactions","^(true|false)$"),
            Map.entry( "showLeaderboard","^(true|false)$")
    );

    private boolean isValidMetadata(SettingQuizMetadataRequest metadata) {
        String key = metadata.getKey();
        String value = metadata.getValue();

        if (!KEY_REGEX_MAP.containsKey(key)) {
            return false;
        }

        String regex = KEY_REGEX_MAP.get(key);

        return value.matches(regex);
    }

    @Override
    public SettingQuizRespone settingQuiz(Long id, SettingQuizRequest body) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz not found"));
        body.getMetadata().forEach(metadata -> settingQuizMetadata(quiz, metadata));
        return SettingQuizRespone.of(quizRepository.save(quiz));
    }
    private void settingQuizMetadata(Quiz quiz,SettingQuizMetadataRequest metadata) {
         QuizSetting quizSetting = findMetadataByKey(quiz, metadata.getKey());
        if (quizSetting == null) {
            quizSetting = new QuizSetting();
            quizSetting.setKey(metadata.getKey());
            quizSetting.setQuiz(quiz);
            quiz.getQuizSettings().add(quizSetting);
        }
        quizSetting.setValue(metadata.getValue());
    }

    private QuizSetting findMetadataByKey(Quiz quiz, String key) {
        return quiz.getQuizSettings().stream()
                .filter(metadata -> metadata.getKey().equals(key))
                .findFirst()
                .orElse(null);
    }
}
