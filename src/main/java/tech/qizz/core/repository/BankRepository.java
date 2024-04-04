package tech.qizz.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.qizz.core.entity.Category;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.entity.User;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<QuizBank, Long> {

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
//@Query("SELECT DISTINCT b FROM QuizBank b " +
//        "LEFT JOIN b.subCategories subcat " +
//        "LEFT JOIN b.manageBanks mb " +
//        "WHERE " +
//        "(" +
//            "b.name LIKE CONCAT('%', :keyword, '%') AND " +
//            "(:draft IS NULL OR b.draft = :draft) AND " +
//            "(:subCategoryId IS NULL OR subcat.subCategoryId IN :subCategoryId) AND " +
//            "(" +
//                ":tab IS NULL OR " +
//                "(:tab = 'created' AND b.createdBy = :user) OR " +
//                "(:tab = 'shared' AND EXISTS (SELECT 1 FROM b.manageBanks mb WHERE :user IN (mb.user))) OR " +
//                "(:tab = 'all' AND (b.createdBy = :user OR EXISTS (SELECT 1 FROM b.manageBanks mb WHERE :user IN (mb.user))))" +
//            ")" +
//        ")"
//)
//Page<QuizBank> findBanks(@Param("keyword") String keyword,
//                         @Param("draft") Boolean draft,
//                         @Param("subCategoryId") List<Long> subCategoryId,
//                         @Param("tab") String tab,
//                         @Param("user") User user,
//                         Pageable pageable);

//    @Query("SELECT DISTINCT b FROM QuizBank b " +
//            "LEFT JOIN b.subCategories subcat " +
//            "LEFT JOIN b.manageBanks mb " +
//            "WHERE " +
//            "(" +
//            "b.name LIKE CONCAT('%', :keyword, '%') AND " +
//            "(:draft IS NULL OR b.draft = :draft) AND " +
//            "(:subCategoryId IS NULL OR " +
//            "ALL(:subCategoryId in (select sc.subCategoryId from b.subCategories sc))" +
//            ") AND " +
//            "(" +
//            ":tab IS NULL OR " +
//            "(:tab = 'created' AND b.createdBy = :user) OR " +
//            "(:tab = 'shared' AND EXISTS (SELECT 1 FROM b.manageBanks mb WHERE :user IN (mb.user))) OR " +
//            "(:tab = 'all' AND (b.createdBy = :user OR EXISTS (SELECT 1 FROM b.manageBanks mb WHERE :user IN (mb.user))))" +
//            ")" +
//            ")"
//    )
//@Query("SELECT DISTINCT b FROM QuizBank b " +
//        "LEFT JOIN b.subCategories subcat " +
//        "LEFT JOIN b.manageBanks mb " +
//        "WHERE " +
//        "(" +
//        "b.name LIKE CONCAT('%', :keyword, '%') AND " +
//        "(:draft IS NULL OR b.draft = :draft) AND " +
//        "(:subCategoryId IS NULL OR " +
//        "(:subCategoryId in (select sc.subCategoryId from b.subCategories sc))" +
//        ") AND " +
//        "(" +
//        ":tab IS NULL OR " +
//        "(:tab = 'created' AND b.createdBy = :user) OR " +
//        "(:tab = 'shared' AND EXISTS (SELECT 1 FROM b.manageBanks mb WHERE :user IN (mb.user))) OR " +
//        "(:tab = 'all' AND (b.createdBy = :user OR EXISTS (SELECT 1 FROM b.manageBanks mb WHERE :user IN (mb.user))))" +
//        ")" +
//        ")"
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
        "LEFT JOIN b.upVoteUsers upv " +
        "LEFT JOIN b.favoriteUsers lik " +
        "WHERE " +
        "(" +
        "b.name LIKE CONCAT('%', :keyword, '%') AND " +
        "(:draft IS NULL OR b.draft = :draft) AND " +
        "(b.disabled = false ) AND " +
        "(:subCategoryId IS NULL OR " +
        "(SELECT COUNT(DISTINCT sc.subCategoryId) FROM b.subCategories sc WHERE sc.subCategoryId IN :subCategoryId) = :subCategoryCount" +
        ") AND " +
        "(" +
        ":tab IS NULL OR " +
        "(:tab = 'created' AND b.createdBy = :user) OR " +
        "(:tab = 'shared' AND EXISTS (SELECT 1 FROM b.manageBanks mb WHERE :user IN (mb.user))) OR " +
        "(:tab = 'upvoted' AND :user = upv) OR " +
        "(:tab = 'liked' AND :user = lik) OR " +
        "(:tab = 'all' AND (b.createdBy = :user OR EXISTS (SELECT 1 FROM b.manageBanks mb WHERE :user IN (mb.user)) OR :user = upv OR :user = lik))" +
        ")" +
        ")"
)
Page<QuizBank> findBanks(@Param("keyword") String keyword,
                         @Param("draft") Boolean draft,
                         @Param("subCategoryId") List<Long> subCategoryId,
                         @Param("subCategoryCount") int subCategoryCount,
                         @Param("tab") String tab,
                         @Param("user") User user,
                         Pageable pageable);


    //    @Query("SELECT b FROM QuizBank b WHERE EXISTS (SELECT 1 FROM b.subCategories subcat WHERE subcat.category = :category)  ORDER BY b.upVoteUsers.size DESC")
//    List<QuizBank> findTop5ByCategoryOrderByUpvotesDesc(Category category);
//    List<QuizBank> findTopByCategoryId(Category id);
@Query("SELECT b FROM QuizBank b WHERE EXISTS (SELECT 1 FROM b.subCategories subcat WHERE subcat.category = :category) AND b.quizPublicity=true AND b.draft=false ORDER BY SIZE(b.upVoteUsers) DESC")
List<QuizBank> findTop5ByCategoryOrderByUpvotesDesc(Category category, Pageable pageable);

    @Query("SELECT DISTINCT b FROM QuizBank b "+
            "LEFT JOIN b.subCategories subcat " +
            "LEFT JOIN b.manageBanks mb " +
            " WHERE " +
            "((b.name LIKE CONCAT('%', :keyword, '%') OR " +
            "b.description LIKE CONCAT('%', :keyword, '%')) " +
            " AND ((b.quizPublicity=true) OR EXISTS (SELECT 1 FROM b.manageBanks mb WHERE :user IN (mb.user)))" +
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
            @Param("user") User user,
            Pageable pageable
    );

}
