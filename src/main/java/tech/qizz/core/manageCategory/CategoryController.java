package tech.qizz.core.manageCategory;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.manageCategory.dto.CategoryResponse;
import tech.qizz.core.manageCategory.dto.CreateCategoryRequest;
import tech.qizz.core.manageCategory.dto.GetAllCategoryResponse;
import tech.qizz.core.manageCategory.dto.UpdateCategoryRequest;
import tech.qizz.core.manageSubCategory.dto.SubCategoryResponse;
import tech.qizz.core.manageUser.dto.UpdateUserRequest;
import tech.qizz.core.manageUser.dto.UsersResponse;

@RestController
@RequestMapping("/category")
@CrossOrigin
@RequiredArgsConstructor

public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    public ResponseEntity<GetAllCategoryResponse> getAllCategories(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "id") String order,
            @RequestParam(required = false, defaultValue = "desc") String sort
    ) {
        GetAllCategoryResponse categories = categoryService.getAllCategories(
                page,
                limit,
                keyword
        );
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        CategoryResponse category = categoryService.getCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest body,
                                                           BindingResult result) {
        if (result.hasErrors() || body == null) {
            throw new BadRequestException("Invalid request");
        }
        CategoryResponse category = categoryService.createCategory(body);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id,
                                                           @Valid @RequestBody UpdateCategoryRequest body, BindingResult result) {
        if (result.hasErrors() || body == null) {
            throw new BadRequestException("Invalid request");
        }
        CategoryResponse category = categoryService.updateCategory(id, body);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<CategoryResponse> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
