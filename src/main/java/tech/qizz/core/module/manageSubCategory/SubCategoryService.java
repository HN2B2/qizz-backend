package tech.qizz.core.module.manageSubCategory;

import tech.qizz.core.module.manageSubCategory.dto.CreateSubCategoryRequest;
import tech.qizz.core.module.manageSubCategory.dto.GetAllSubCategoryResponse;
import tech.qizz.core.module.manageSubCategory.dto.SubCategoryResponse;
import tech.qizz.core.module.manageSubCategory.dto.UpdateSubCategoryRequest;

public interface SubCategoryService {

    GetAllSubCategoryResponse getAllSubCategories(Integer page, Integer limit, String keyword);

    SubCategoryResponse getSubCategoryById(Long id);

    SubCategoryResponse createSubCategory(CreateSubCategoryRequest request, Long categoryId);

    SubCategoryResponse updateSubCategory(Long categoryId, Long subCategoryId, UpdateSubCategoryRequest request);

    void deleteSubCategory(Long categoryId, Long subCategoryId);  // Removed Long id parameter

    GetAllSubCategoryResponse getAllSubCategoriesByCategoryId(Long categoryId, Integer page, Integer limit);
}
