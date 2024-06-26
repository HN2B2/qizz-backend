package tech.qizz.core.module.settingQuiz;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.Quiz;
import tech.qizz.core.entity.QuizSetting;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.repository.QuizRepository;
import tech.qizz.core.module.settingQuiz.dto.SettingQuizMetadataRequest;
import tech.qizz.core.module.settingQuiz.dto.SettingQuizRequest;
import tech.qizz.core.module.settingQuiz.dto.SettingQuizRespone;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SettingQuizServiceImpl implements SettingQuizService {
    private final QuizRepository quizRepository;

    private static final Map<String, String> KEY_REGEX_MAP = Map.of(
            "attempt","^[1-5]{1}$|Unlimited",
            "showAnswersDuringAct","^(true|false)$",
            "showAnswersAfterAct","^(true|false)$",
            "shuffleQuestions","^(true|false)$",
            "shuffleAnswers","^(true|false)$",
            "skipQuestion","^(true|false)$",
            "powerUp","^(true|false)$",
             "reactions","^(true|false)$",
             "showLeaderboard","^(true|false)$"
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
        body.getMetadata().forEach(metadata -> {
            if (!isValidMetadata(metadata)) {
                throw new BadRequestException("Invalid metadata");
            }
        });
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
