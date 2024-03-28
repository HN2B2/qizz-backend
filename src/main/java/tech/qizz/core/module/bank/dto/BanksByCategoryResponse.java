package tech.qizz.core.module.bank.dto;

import lombok.*;
import tech.qizz.core.module.manageCategory.dto.CategoryResponse;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor

public class BanksByCategoryResponse {
    private CategoryResponse category;
    private List<BankResponse> banks;
}
