package tech.qizz.core.module.takeQuiz.dto.playing;

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
public class PlayingResponse<T> {

    private PlayingState state;

    private T data;
}
