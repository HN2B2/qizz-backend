package tech.qizz.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import tech.qizz.core.entity.ManageBank;
import tech.qizz.core.entity.QuizBank;

import java.util.List;

public interface ManageBankRepository extends JpaRepository<ManageBank, Long> {
    List<ManageBank> findAllByQuizBank(QuizBank quizBank);
    @Transactional
    void deleteAllByQuizBank(QuizBank quizBank);

}
