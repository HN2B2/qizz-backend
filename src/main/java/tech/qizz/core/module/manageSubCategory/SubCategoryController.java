package tech.qizz.core.module.manageSubCategory;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.module.manageSubCategory.dto.CreateSubCategoryRequest;
import tech.qizz.core.module.manageSubCategory.dto.GetAllSubCategoryResponse;
import tech.qizz.core.module.manageSubCategory.dto.SubCategoryResponse;
import tech.qizz.core.module.manageSubCategory.dto.UpdateSubCategoryRequest;

@RestController
@RequestMapping("/subcategories")
@RequiredArgsConstructor
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    @GetMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    public ResponseEntity<GetAllSubCategoryResponse> getAllSubCategoriesByCategoryId(
        @PathVariable Long categoryId,
        @RequestParam(required = false, defaultValue = "1") Integer page,
        @RequestParam(required = false, defaultValue = "10") Integer limit
    ) {
        GetAllSubCategoryResponse subCategories = subCategoryService.getAllSubCategoriesByCategoryId(
            categoryId,
            page,
            limit
        );
        return new ResponseEntity<>(subCategories, HttpStatus.OK);
    }

    @PostMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<SubCategoryResponse> createSubCategory(
        @Valid @RequestBody CreateSubCategoryRequest body, @PathVariable Long categoryId,
        BindingResult result) {
        if (result.hasErrors() || body == null) {
            throw new BadRequestException("Invalid request");
        }
        SubCategoryResponse subCategory = subCategoryService.createSubCategory(body, categoryId);
        return new ResponseEntity<>(subCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<SubCategoryResponse> updateSubCategory(
        @PathVariable Long categoryId,
        @PathVariable Long id,
        @Valid @RequestBody UpdateSubCategoryRequest body,
        BindingResult result
    ) {
        if (result.hasErrors() || body == null) {
            throw new BadRequestException("Invalid request");
        }
        SubCategoryResponse subCategory = subCategoryService.updateSubCategory(categoryId, id,
            body);
        return new ResponseEntity<>(subCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<SubCategoryResponse> deleteSubCategoryById(
        @PathVariable Long categoryId,
        @PathVariable Long id
    ) {
        subCategoryService.deleteSubCategory(categoryId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
