package tech.qizz.core.module.settingQuiz;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.qizz.core.module.settingQuiz.dto.SettingQuizRequest;
import tech.qizz.core.module.settingQuiz.dto.SettingQuizRespone;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class SettingQuizController {

    private final SettingQuizService settingQuizService;

    @PutMapping("live_quiz/{quiz_id}")
    @PreAuthorize("hasAnyAuthority('USER', 'STAFF', 'ADMIN')")
    public ResponseEntity<SettingQuizRespone> settingQuiz(@PathVariable("quiz_id") Long quizId,
        @Valid @RequestBody SettingQuizRequest body) {
        return new ResponseEntity<>(settingQuizService.settingQuiz(quizId, body),
            HttpStatus.CREATED);
    }

}
