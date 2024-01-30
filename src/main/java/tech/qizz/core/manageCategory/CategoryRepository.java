package tech.qizz.core.manageCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.qizz.core.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c " +
        "JOIN FETCH c.subCategories sc WHERE " +
        "(c.name LIKE CONCAT('%', :keyword, '%') OR " +
        "c.description LIKE CONCAT('%', :keyword, '%'))"
    )
    Page<Category> findCategoriesByKeyword(
        @Param("keyword") String keyword,
        Pageable pageable
    );
//    Page<Category> findCategoriesByNameLike(String name, Pageable pageable);
//    Page<Category> findCategoriesByCreatedAtBetween(Date startDate, Date endDate, Pageable pageable);
//    Page<Category> findCategoriesByModifiedAtBetween(Date startDate, Date endDate, Pageable pageable);
}
