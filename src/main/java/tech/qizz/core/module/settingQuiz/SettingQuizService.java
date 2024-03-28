package tech.qizz.core.module.settingQuiz;

import tech.qizz.core.module.settingQuiz.dto.SettingQuizRequest;
import tech.qizz.core.module.settingQuiz.dto.SettingQuizRespone;

public interface SettingQuizService {
    SettingQuizRespone settingQuiz(Long id, SettingQuizRequest body);
}
