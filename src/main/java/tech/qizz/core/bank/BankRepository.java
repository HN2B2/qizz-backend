package tech.qizz.core.bank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.entity.User;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<QuizBank, Long> {

//    @Query("SELECT b FROM QuizBank b WHERE " +
//            "(b.name LIKE CONCAT('%', :keyword, '%') AND " +
////            "b.description LIKE CONCAT('%', :keyword, '%')) AND " +
//            "(:draft IS NULL OR b.draft = :draft) AND " +
//            "(:subCategoryId IS NULL OR b.subCategories.map(sc -> sc.id).contains(:subCategoryId) AND " +
//            "(:tab IS NULL OR (:tab = 'created' AND b.createdBy = :user) OR (:tab = 'shared' AND b.manageBanks.map(mb -> mb.user).contains(:user))))"
//
////            "b.description LIKE CONCAT('%', :keyword, '%'))"
//    )
//    Page<QuizBank> findBanks(
//            @Param("keyword") String keyword,
//            @Param("draft") Boolean draft,
//            @Param("subCategoryIds") List<Long> subCategoryIds,
//            @Param("tab") String tab,
//            Pageable pageable
//    );
//@Query("SELECT b FROM QuizBank b WHERE " +
//        "(b.name LIKE CONCAT('%', :keyword, '%') AND " +
//        "(:draft IS NULL OR b.draft = :draft) AND " +
//        "(:subCategoryId IS NULL OR b.subCategories.map(sc -> sc.subCategoryId).contains(:subCategoryId) AND " +
//        "(:tab IS NULL OR (:tab = 'created' AND b.createdBy = :user) OR (:tab = 'shared' AND b.manageBanks.map(mb -> mb.user).contains(:user)) )"
//)
//Page<QuizBank> findBanks(@Param("keyword") String keyword,
//                         @Param("draft") Boolean draft,
//                         @Param("subCategoryId") List<Long> subCategoryId,
//                         @Param("tab") String tab,
//                         @Param("user") User user,
//                         Pageable pageable);
@Query("SELECT DISTINCT b FROM QuizBank b " +
        "LEFT JOIN b.subCategories subcat " +
        "LEFT JOIN b.manageBanks mb " +
        "WHERE " +
        "(b.name LIKE CONCAT('%', :keyword, '%') AND " +
        "(:draft IS NULL OR b.draft = :draft) AND " +
        "(:subCategoryId IS NULL OR subcat.subCategoryId IN :subCategoryId) AND " +
        "(:tab IS NULL OR (:tab = 'created' AND b.createdBy = :user) OR (:tab = 'shared' AND EXISTS (SELECT 1 FROM b.manageBanks mb WHERE :user IN (mb.user)))))"
)
Page<QuizBank> findBanks(@Param("keyword") String keyword,
                         @Param("draft") Boolean draft,
                         @Param("subCategoryId") List<Long> subCategoryId,
                         @Param("tab") String tab,
                         @Param("user") User user,
                         Pageable pageable);



}
