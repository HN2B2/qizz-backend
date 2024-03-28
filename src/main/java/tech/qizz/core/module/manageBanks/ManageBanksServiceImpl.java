// ManageBanksServiceImpl
package tech.qizz.core.module.manageBanks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.entity.constant.UserRole;
import tech.qizz.core.exception.ForbiddenException;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.module.manageBanks.dto.BankResponse;
import tech.qizz.core.module.manageBanks.dto.GetAllBanksResponse;

import java.util.List;
import tech.qizz.core.repository.ManageBanksRepository;

@Service
public class ManageBanksServiceImpl implements ManageBanksService {

    private final ManageBanksRepository manageBanksRepository;

    @Autowired
    public ManageBanksServiceImpl(ManageBanksRepository manageBanksRepository) {
        this.manageBanksRepository = manageBanksRepository;
    }

    @Override
    public GetAllBanksResponse getAllBanks(
            Integer page,
            Integer limit,
            String keyword,
            String order,
            String sort,
            List<Long> subCategoryIds,
            Integer mi,
            Integer ma) {
        Sort sortType = sort.equalsIgnoreCase("asc") ? Sort.by(order) : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page - 1, limit, sortType);
        Page<QuizBank> banks = manageBanksRepository.findBanksByKeyword(keyword, (subCategoryIds==null||subCategoryIds.isEmpty())?null:subCategoryIds, (subCategoryIds==null||subCategoryIds.isEmpty())?0:subCategoryIds.size(), mi, ma, pageable);
        return GetAllBanksResponse.of(banks);
    }

    @Override
    public BankResponse getBankById(Long id) {
        QuizBank bank = manageBanksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank not found with id: " + id));
        return BankResponse.of(bank);
    }

    @Override
    public void deleteBank(Long id) {

        QuizBank bank = manageBanksRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bank not found"));


        bank.setDisabled(true);
        manageBanksRepository.save(bank);
    }
}
