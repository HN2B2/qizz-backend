package tech.qizz.core.module.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class UpvoteResponse {
    @JsonProperty("isUpvoted")
    private Boolean isUpvoted;
}
