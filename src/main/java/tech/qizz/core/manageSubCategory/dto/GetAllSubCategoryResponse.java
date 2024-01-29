package tech.qizz.core.manageSubCategory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import tech.qizz.core.entity.SubCategory; // Correct import

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllSubCategoryResponse {

    @JsonProperty("data")
    private List<SubCategoryResponse> data;

    @JsonProperty("total")
    private Long total;

    public static GetAllSubCategoryResponse of(Page<SubCategory> subCategories) {
        return GetAllSubCategoryResponse.builder()
                .data(subCategories.map(SubCategoryResponse::of).getContent()) // Use map instead of stream().map()
                .total(subCategories.getTotalElements())
                .build();
    }
}
