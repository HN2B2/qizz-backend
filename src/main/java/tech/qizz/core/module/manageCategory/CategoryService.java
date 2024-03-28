package tech.qizz.core.module.manageCategory;

import tech.qizz.core.module.manageCategory.dto.CategoryResponse;
import tech.qizz.core.module.manageCategory.dto.CreateCategoryRequest;
import tech.qizz.core.module.manageCategory.dto.GetAllCategoryResponse;
import tech.qizz.core.module.manageCategory.dto.UpdateCategoryRequest;

public interface CategoryService {

    GetAllCategoryResponse getAllCategories(Integer page, Integer limit, String keyword,
        String order, String sort);

    CategoryResponse getCategoryById(Long id);

    CategoryResponse createCategory(CreateCategoryRequest request);

    CategoryResponse updateCategory(Long id, UpdateCategoryRequest request);

    void deleteCategory(Long id);
}
