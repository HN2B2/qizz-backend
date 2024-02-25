package tech.qizz.core.takeQuiz.dto.playing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRankingResponse {

    private String displayName;
    private long score;
    private int correctCount;
    private int wrongCount;
}
