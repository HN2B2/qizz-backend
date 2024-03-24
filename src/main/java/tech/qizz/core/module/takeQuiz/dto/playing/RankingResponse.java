package tech.qizz.core.module.takeQuiz.dto.playing;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
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
public class RankingResponse {

    @JsonProperty("totalQuestion")
    private int totalQuestion;

    @JsonProperty("currentQuestion")
    private int currentQuestion;

    @JsonProperty("players")
    private List<UserRankingResponse> players;
}
