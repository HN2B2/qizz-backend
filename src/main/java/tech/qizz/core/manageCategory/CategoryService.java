package tech.qizz.core.manageCategory;

import tech.qizz.core.manageCategory.dto.CreateCategoryRequest;
import tech.qizz.core.manageCategory.dto.GetAllCategoryResponse;
import tech.qizz.core.manageCategory.dto.CategoryResponse;
import tech.qizz.core.manageCategory.dto.UpdateCategoryRequest;

public interface CategoryService {
    GetAllCategoryResponse getAllCategories(Integer page, Integer limit, String keyword);

    CategoryResponse getCategoryById(Long id);

    CategoryResponse createCategory(CreateCategoryRequest request);

    CategoryResponse updateCategory(Long id, UpdateCategoryRequest request);

    void deleteCategory(Long id);
}
