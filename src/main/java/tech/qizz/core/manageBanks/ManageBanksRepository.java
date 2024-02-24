package tech.qizz.core.manageBanks;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.qizz.core.entity.QuizBank;

@Repository
public interface ManageBanksRepository extends JpaRepository<QuizBank, Long> {

    @Query("SELECT b FROM QuizBank b WHERE " +
            "(b.name LIKE CONCAT('%', :keyword, '%') OR " +
            "b.description LIKE CONCAT('%', :keyword, '%'))"
    )
    Page<QuizBank> findBanksByKeyword(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    // Add additional methods if needed based on your requirements
}
