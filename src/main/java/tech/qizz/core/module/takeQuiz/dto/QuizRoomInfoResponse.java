package tech.qizz.core.module.takeQuiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.qizz.core.entity.constant.QuizState;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizRoomInfoResponse<T> {

    @JsonProperty("quizCode")
    private String quizCode;

    @JsonProperty("quizName")
    private String quizName;

    @JsonProperty("state")
    private QuizState state;

    @JsonProperty("data")
    private T data;
}
