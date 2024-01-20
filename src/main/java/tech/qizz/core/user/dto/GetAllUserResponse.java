package tech.qizz.core.user.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.domain.Page;
import tech.qizz.core.entity.User;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllUserResponse {
    @JsonProperty("data")
    private List<UserResponse> data;
    @JsonProperty("total")
    private Long total;

    public static GetAllUserResponse of (Page<User> users) {
        return GetAllUserResponse.builder()
                .data(users.stream().map(UserResponse::of).toList())
                .total(users.getTotalElements())
                .build();
    }
}
