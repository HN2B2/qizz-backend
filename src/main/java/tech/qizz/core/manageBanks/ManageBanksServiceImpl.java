// ManageBanksServiceImpl
package tech.qizz.core.manageBanks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.manageBanks.dto.BankResponse;
import tech.qizz.core.manageBanks.dto.GetAllBanksResponse;

import java.util.Date;
import java.util.List;

@Service
public class ManageBanksServiceImpl implements ManageBanksService {

    private final ManageBanksRepository manageBanksRepository;

    @Autowired
    public ManageBanksServiceImpl(ManageBanksRepository manageBanksRepository) {
        this.manageBanksRepository = manageBanksRepository;
    }

    @Override
    public GetAllBanksResponse getAllBanks(Integer page, Integer limit, String keyword, String order, String sort) {
        Sort sortType = sort.equalsIgnoreCase("asc") ? Sort.by(order) : Sort.by(order).descending();
        PageRequest pageable = PageRequest.of(page - 1, limit, sortType);
        Page<QuizBank> banks = manageBanksRepository.findBanksByKeyword(keyword, pageable);
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
        manageBanksRepository.deleteById(id);
    }
}
