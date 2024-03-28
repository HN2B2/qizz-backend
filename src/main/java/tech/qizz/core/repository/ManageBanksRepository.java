package tech.qizz.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.qizz.core.entity.QuizBank;

import java.util.List;

@Repository
public interface ManageBanksRepository extends JpaRepository<QuizBank, Long> {

    @Query("SELECT DISTINCT b FROM QuizBank b "+
            "LEFT JOIN b.subCategories subcat " +
            " WHERE " +
            "((b.name LIKE CONCAT('%', :keyword, '%') OR " +
            "b.description LIKE CONCAT('%', :keyword, '%')) " +
            " AND (:mi IS NULL OR SIZE(b.questions) >= :mi)" +
            " AND (:ma IS NULL OR SIZE(b.questions) <= :ma)" +
            " AND b.disabled=false" +
            " AND (:subCategoryId IS NULL OR " +
            "(SELECT COUNT(DISTINCT sc.subCategoryId) FROM b.subCategories sc WHERE sc.subCategoryId IN :subCategoryId) = :subCategoryCount" +
            ")" +

            ")"
    )
    Page<QuizBank> findBanksByKeyword(
            @Param("keyword") String keyword,
            @Param("subCategoryId") List<Long> subCategoryId,
            @Param("subCategoryCount") int subCategoryCount,
            @Param("mi") Integer mi, @Param("ma") Integer ma,
            Pageable pageable
    );

    // Add additional methods if needed based on your requirements
}
