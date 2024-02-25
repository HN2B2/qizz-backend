package tech.qizz.core.settingQuiz;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.qizz.core.settingQuiz.dto.SettingQuizRequest;
import tech.qizz.core.settingQuiz.dto.SettingQuizRespone;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
@CrossOrigin
public class SettingQuizController {
    private final SettingQuizService settingQuizService;
    @GetMapping("{quiz_id}/live_quiz")
    @PreAuthorize("hasAnyAuthority('USER', 'STAFF', 'ADMIN')")
    public ResponseEntity<SettingQuizRespone> getSettingQuiz(@PathVariable("quiz_id") Long id) {
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PostMapping("{quiz_id}/live_quiz")
    @PreAuthorize("hasAnyAuthority('USER', 'STAFF', 'ADMIN')")
    public ResponseEntity<SettingQuizRespone> settingQuiz(@PathVariable("quiz_id") Long quizId, @Valid @RequestBody SettingQuizRequest body){
        return new ResponseEntity<>( settingQuizService.settingQuiz(quizId, body),HttpStatus.CREATED);
    }

}
