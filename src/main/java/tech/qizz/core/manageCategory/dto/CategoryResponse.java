package tech.qizz.core.manageCategory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.qizz.core.entity.Category;
import tech.qizz.core.manageSubCategory.dto.SubCategoryResponse;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("subCategories")
    private List<SubCategoryResponse> subCategories;

    public static CategoryResponse of(Category category) {
        return CategoryResponse.builder()
            .id(category.getCategoryId())
            .name(category.getName())
            .description(category.getDescription())
            .subCategories(
                category.getSubCategories().stream().map(SubCategoryResponse::of).toList())
            .build();
    }
}
