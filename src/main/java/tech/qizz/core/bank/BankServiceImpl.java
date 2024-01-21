package tech.qizz.core.bank;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.qizz.core.bank.dto.BankRequest;
import tech.qizz.core.bank.dto.BankResponse;
import tech.qizz.core.entity.QuizBank;

import java.util.Optional;

@AllArgsConstructor
@Service
public class BankServiceImpl implements BankService{

    private BankRepository bankRepository;

    @Override
    public BankResponse getBankResponseById(Long id) {
        Optional<QuizBank> bank = bankRepository.findById(id);
        return bank.map(BankResponse::of).orElse(null);
    }

    public BankResponse saveBank(QuizBank bank) {
            QuizBank savedBank = bankRepository.save(bank);
            return BankResponse.of(savedBank);
    }
}
