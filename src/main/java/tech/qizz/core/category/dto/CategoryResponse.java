package tech.qizz.core.category.dto;

import lombok.*;
import tech.qizz.core.entity.Category;
import tech.qizz.core.entity.SubCategory;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class CategoryResponse {
    private Long categoryId;
    private String name;
    private Date createdAt;
    private Date modifiedAt;
    private String description;
    private List<SubCategory> subCategories;

    public static CategoryResponse of (Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .createdAt(category.getCreatedAt())
                .modifiedAt(category.getModifiedAt())
                .description(category.getDescription())
                .subCategories(category.getSubCategories())
                .build();
    }
}
