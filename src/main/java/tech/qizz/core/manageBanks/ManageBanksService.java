// ManageBanksService
package tech.qizz.core.manageBanks;

import org.springframework.stereotype.Service;
import tech.qizz.core.manageBanks.dto.BankResponse;
import tech.qizz.core.manageBanks.dto.GetAllBanksResponse;

import java.util.List;

@Service
public interface ManageBanksService {

    GetAllBanksResponse getAllBanks(Integer page, Integer limit, String keyword, String order, String sort);

    BankResponse getBankById(Long id);

    void deleteBank(Long id);
}
