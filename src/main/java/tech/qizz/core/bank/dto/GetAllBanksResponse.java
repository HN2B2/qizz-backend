package tech.qizz.core.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.manageCategory.dto.CategoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

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
            .data(banks
                .stream()
                .map(BankResponse::of)
                .toList())
            .total(banks.getTotalElements())
            .build();
    }
}
