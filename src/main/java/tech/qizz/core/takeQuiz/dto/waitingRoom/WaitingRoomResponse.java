package tech.qizz.core.takeQuiz.dto.waitingRoom;

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
public class WaitingRoomResponse {

    @JsonProperty("joinSate")
    private JoinState joinSate;

    @JsonProperty("message")
    private String message;

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("current")
    private Integer current;

    @JsonProperty("users")
    private List<RoomUserResponse> users;
}
