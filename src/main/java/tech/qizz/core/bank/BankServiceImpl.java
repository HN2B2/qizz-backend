package tech.qizz.core.bank;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.qizz.core.bank.dto.CreateBankRequest;
import tech.qizz.core.bank.dto.BankResponse;
import tech.qizz.core.entity.ManageBank;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.entity.User;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.manageBank.ManageBankRepository;
import tech.qizz.core.user.UserRepository;
import tech.qizz.core.user.UserService;
import tech.qizz.core.user.dto.UserResponse;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BankServiceImpl implements BankService{

    private BankRepository bankRepository;
    private UserRepository userRepository;
    private ManageBankRepository manageBankRepository;

    @Override
    public BankResponse getBankResponseById(Long id) {
        Optional<QuizBank> bank = bankRepository.findById(id);
        return bank.map(BankResponse::of).orElseThrow(() -> new NotFoundException("Bank not found"));
    }

    public BankResponse saveBank(CreateBankRequest bank, User user) {
//        bank.getManageUsers().stream().map
//        Optional<ManageBank> manageBank = manageBankRepository.save()
        Set<User> manageUsers = bank
                .getManageUsers()
                .stream()
                .map((manageUser) -> userRepository.findByEmail(manageUser.getEmail())
                        .orElseThrow(() -> new NotFoundException("Email " + manageUser.getEmail() + " not found")))
                .collect(Collectors.toSet());

            QuizBank savedBank = QuizBank.builder()
                    .name(bank.getName())
                    .description(bank.getDescription())
                    .featuresImage(bank.getFeaturesImage())
                    .quizPublicity(bank.getQuizPublicity())
                    .publicEditable(bank.getPublicEditable())
                    .draft(bank.getDraft())
                    .createdBy(user)
                    .modifiedBy(user)
                    .manageUsers(manageUsers)
                    .build();
            QuizBank newBank = bankRepository.save(savedBank);
            return BankResponse.of(newBank);
    }
}
