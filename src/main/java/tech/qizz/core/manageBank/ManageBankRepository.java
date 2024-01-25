package tech.qizz.core.manageBank;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.qizz.core.entity.ManageBank;

public interface ManageBankRepository extends JpaRepository<ManageBank, Long> {
}
