package tech.qizz.core.manageUser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import tech.qizz.core.entity.User;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllUserResponse {

    @JsonProperty("data")
    private List<UsersResponse> data;
    @JsonProperty("total")
    private Long total;

    public static GetAllUserResponse of(Page<User> users) {
        return GetAllUserResponse.builder()
            .data(users.stream().map(UsersResponse::of).toList())
            .total(users.getTotalElements())
            .build();
    }
}
