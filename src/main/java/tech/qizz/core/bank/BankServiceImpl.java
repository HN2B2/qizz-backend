package tech.qizz.core.bank;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.qizz.core.bank.dto.BankRequest;
import tech.qizz.core.bank.dto.BankResponse;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.entity.User;
import tech.qizz.core.exception.NotFoundException;

import java.util.Optional;

@AllArgsConstructor
@Service
public class BankServiceImpl implements BankService{

    private BankRepository bankRepository;

    @Override
    public BankResponse getBankResponseById(Long id) {
        Optional<QuizBank> bank = bankRepository.findById(id);
        return bank.map(BankResponse::of).orElseThrow(() -> new NotFoundException("Bank not found"));
    }

    public BankResponse saveBank(BankRequest bank, User user) {
            QuizBank savedBank = QuizBank.builder()
                    .name(bank.getName())
                    .description(bank.getDescription())
                    .featuresImage(bank.getFeaturesImage())
                    .quizPublicity(bank.getQuizPublicity())
                    .publicEditable(bank.getPublicEditable())
                    .draft(bank.getDraft())
                    .createdBy(user)
                    .modifiedBy(user)
                    .build();

            return BankResponse.of(bankRepository.save(savedBank));
    }
}
