package tech.qizz.core.manageBanks.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import tech.qizz.core.entity.QuizBank;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class GetAllBanksResponse {

    @JsonProperty("data")
    private List<BankResponse> data;

    @JsonProperty("total")
    private Long total;

    public static GetAllBanksResponse of(Page<QuizBank> banks) {
        return GetAllBanksResponse.builder()
                .data(banks.stream().map(BankResponse::of).toList())
                .total(banks.getTotalElements())
                .build();
    }
}
