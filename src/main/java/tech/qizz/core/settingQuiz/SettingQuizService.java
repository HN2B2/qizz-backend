package tech.qizz.core.settingQuiz;

import tech.qizz.core.entity.Quiz;
import tech.qizz.core.settingQuiz.dto.SettingQuizMetadataRequest;
import tech.qizz.core.settingQuiz.dto.SettingQuizRequest;
import tech.qizz.core.settingQuiz.dto.SettingQuizRespone;

public interface SettingQuizService {
    SettingQuizRespone settingQuiz(Long id, SettingQuizRequest body);
}
