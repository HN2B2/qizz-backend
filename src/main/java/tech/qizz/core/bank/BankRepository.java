package tech.qizz.core.bank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.qizz.core.entity.QuizBank;
@Repository
public interface BankRepository extends JpaRepository<QuizBank, Long> {
}
