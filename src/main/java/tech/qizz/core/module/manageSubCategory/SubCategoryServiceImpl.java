package tech.qizz.core.module.manageSubCategory;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.SubCategory;
import tech.qizz.core.repository.CategoryRepository;
import tech.qizz.core.module.manageSubCategory.dto.CreateSubCategoryRequest;
import tech.qizz.core.module.manageSubCategory.dto.GetAllSubCategoryResponse;
import tech.qizz.core.module.manageSubCategory.dto.SubCategoryResponse;
import tech.qizz.core.module.manageSubCategory.dto.UpdateSubCategoryRequest;

import java.util.Date;
import tech.qizz.core.repository.SubCategoryRepository;

@Service
@AllArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

//    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository) {
//        this.subCategoryRepository = subCategoryRepository;
//        this.categoryRepository = categoryRepository;
//    }

    @Override
    public GetAllSubCategoryResponse getAllSubCategories(Integer page, Integer limit, String keyword) {
        PageRequest pageRequest = PageRequest.of(page - 1, limit);
        Page<SubCategory> subCategories = subCategoryRepository.findSubCategoriesByKeyword(keyword, pageRequest);
        return GetAllSubCategoryResponse.of(subCategories);
    }

    @Override
    public SubCategoryResponse getSubCategoryById(Long id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCategory not found with id: " + id));
        return SubCategoryResponse.of(subCategory);
    }

    @Override
    public SubCategoryResponse createSubCategory(CreateSubCategoryRequest request, Long categoryId) {
        SubCategory subCategory = SubCategory.builder()
                .name(request.getName())
                .description(request.getDescription())
//                .createdAt(new Date())
//                .modifiedAt(new Date())
                .category(categoryRepository.findById(categoryId).get())
                .build();

        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
        return SubCategoryResponse.of(savedSubCategory);
    }

    @Override
    public SubCategoryResponse updateSubCategory(Long categoryId, Long subCategoryId, UpdateSubCategoryRequest request) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new RuntimeException("SubCategory not found with id: " + subCategoryId));

        // Assuming you have a reference to Category in SubCategory entity
        // Replace 'getCategory()' with the actual field/method in SubCategory entity representing the Category relationship
//        if (!subCategory.getCategory().getCategoryId().equals(categoryId)) {
//            throw new RuntimeException("SubCategory with id " + subCategoryId + " does not belong to Category with id " + categoryId);
//        }

        subCategory.setName(request.getName());
        subCategory.setDescription(request.getDescription());
        subCategory.setModifiedAt(new Date());

        SubCategory updatedSubCategory = subCategoryRepository.save(subCategory);
        return SubCategoryResponse.of(updatedSubCategory);
    }

    @Override
    public void deleteSubCategory(Long categoryId, Long subCategoryId) {
        subCategoryRepository.deleteById(subCategoryId);
    }

    @Override
    public GetAllSubCategoryResponse getAllSubCategoriesByCategoryId(Long categoryId, Integer page, Integer limit) {
        PageRequest pageRequest = PageRequest.of(page - 1, limit);
        Page<SubCategory> subCategories = subCategoryRepository.findSubCategoriesByCategoryId(categoryId, pageRequest);
        return GetAllSubCategoryResponse.of(subCategories);
    }
}
