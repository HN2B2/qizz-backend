package tech.qizz.core.manageCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.Category;
import tech.qizz.core.manageCategory.dto.CategoryResponse;
import tech.qizz.core.manageCategory.dto.CreateCategoryRequest;
import tech.qizz.core.manageCategory.dto.GetAllCategoryResponse;
import tech.qizz.core.manageCategory.dto.UpdateCategoryRequest;

import java.util.Date;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public GetAllCategoryResponse getAllCategories(Integer page, Integer limit, String keyword) {
        PageRequest pageRequest = PageRequest.of(page - 1, limit);
        Page<Category> categories = categoryRepository.findCategoriesByKeyword(keyword, pageRequest);
        return GetAllCategoryResponse.of(categories);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return CategoryResponse.of(category);
    }

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
//                .createdAt(new Date())
//                .modifiedAt(new Date())
                .build();

        Category savedCategory = categoryRepository.save(category);
        return CategoryResponse.of(savedCategory);
    }

    @Override
    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        category.setModifiedAt(new Date());

        Category updatedCategory = categoryRepository.save(category);
        return CategoryResponse.of(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
