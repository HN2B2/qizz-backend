package tech.qizz.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.qizz.core.entity.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    @Query("SELECT sc FROM SubCategory sc WHERE " +
            "(sc.name LIKE CONCAT('%', :keyword, '%') OR " +
            "sc.description LIKE CONCAT('%', :keyword, '%'))"
    )
    Page<SubCategory> findSubCategoriesByKeyword(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query("SELECT sc FROM SubCategory sc WHERE sc.category.id = :categoryId")
    Page<SubCategory> findSubCategoriesByCategoryId(
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );
}
