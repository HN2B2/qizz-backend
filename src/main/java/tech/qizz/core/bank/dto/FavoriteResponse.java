package tech.qizz.core.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class FavoriteResponse {

    @JsonProperty("isLiked")
    private Boolean isLiked;
}
