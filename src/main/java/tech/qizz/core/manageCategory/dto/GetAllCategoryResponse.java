package tech.qizz.core.manageCategory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import tech.qizz.core.entity.Category;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCategoryResponse {

    @JsonProperty("data")
    private List<CategoryResponse> data;

    @JsonProperty("total")
    private Long total;

    public static GetAllCategoryResponse of(Page<Category> categories) {
        return GetAllCategoryResponse.builder()
                .data(categories.stream().map(CategoryResponse::of).toList())
                .total(categories.getTotalElements())
                .build();
    }
}
