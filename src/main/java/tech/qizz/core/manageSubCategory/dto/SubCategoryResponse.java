package tech.qizz.core.manageSubCategory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import tech.qizz.core.entity.Category;
import tech.qizz.core.entity.SubCategory;
import tech.qizz.core.manageCategory.dto.CategoryResponse;
@Builder
public class SubCategoryResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    public static SubCategoryResponse of(SubCategory subCategories) {
        return (SubCategoryResponse.builder()
                .name(subCategories.getName())
                .description(subCategories.getDescription())
                .build());
    }
}
