package tech.qizz.core.manageSubCategory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import tech.qizz.core.entity.SubCategory;

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

    public static SubCategoryResponse of(SubCategory subCategories) {
        return (SubCategoryResponse.builder()
            .id(subCategories.getSubCategoryId())
            .createdAt(subCategories.getCreatedAt().toString())
            .name(subCategories.getName())
            .description(subCategories.getDescription())
            .build());
    }
}
