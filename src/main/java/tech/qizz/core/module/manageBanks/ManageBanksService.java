// ManageBanksService
package tech.qizz.core.module.manageBanks;

import org.springframework.stereotype.Service;
import tech.qizz.core.module.manageBanks.dto.BankResponse;
import tech.qizz.core.module.manageBanks.dto.GetAllBanksResponse;

import java.util.List;

@Service
public interface ManageBanksService {

    GetAllBanksResponse getAllBanks(Integer page, Integer limit, String keyword, String order, String sort, List<Long> subCategoryIds, Integer mi, Integer ma);

    BankResponse getBankById(Long id);

    void deleteBank(Long id);
}
