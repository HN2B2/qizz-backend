package tech.qizz.core.manageSubCategory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import tech.qizz.core.entity.Category;
import tech.qizz.core.entity.SubCategory;
import tech.qizz.core.manageCategory.dto.CategoryResponse;

@Builder
public class SubCategoryResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("categoryId")
    private Long categoryId;

    @JsonProperty("categoryName")
    private String categoryName;

    public static SubCategoryResponse of(SubCategory subCategories) {
        return (SubCategoryResponse.builder()
            .id(subCategories.getSubCategoryId())
            .createdAt(subCategories.getCreatedAt().toString())
            .name(subCategories.getName())
            .description(subCategories.getDescription())
            .categoryId(subCategories.getCategory().getCategoryId())
            .categoryName(subCategories.getCategory().getName())
            .build());
    }
}
