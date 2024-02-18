package tech.qizz.core.takeQuiz.dto;

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
public class QuizRoomInfoResponse {

    @JsonProperty("quizCode")
    private String quizCode;

    @JsonProperty("quizName")
    private String quizName;

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("current")
    private Integer current;

    @JsonProperty("started")
    private boolean started;

    @JsonProperty("ended")
    private boolean ended;

    @JsonProperty("users")
    private List<RoomUserResponse> users;
}
