package tech.qizz.core.manageBank;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.qizz.core.bank.BankRepository;
import tech.qizz.core.entity.ManageBank;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.entity.User;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.manageBank.dto.CreateManageBankRequest;
import tech.qizz.core.manageBank.dto.ManageBankResponse;
import tech.qizz.core.manageUser.UserRepository;


@Service
@AllArgsConstructor
public class ManageBankServiceImpl implements ManageBankService {

    private ManageBankRepository manageBankRepository;
    private BankRepository bankRepository;
    private UserRepository userRepository;

    @Override
    public List<ManageBankResponse> getAllManageBanksByBankId(Long bankId) {
        QuizBank quizBank = bankRepository.findById(bankId)
            .orElseThrow(() -> new NotFoundException("Bank not found"));
        return manageBankRepository.findAllByQuizBank(quizBank).stream().map(ManageBankResponse::of)
            .toList();
    }

    @Override
    public ManageBankResponse createManageBank(Long bankId, CreateManageBankRequest body) {
        QuizBank quizBank = bankRepository.findById(bankId)
            .orElseThrow(() -> new NotFoundException("Bank not found"));
        User user = userRepository.findByEmail(body.getEmail())
            .orElseThrow(() -> new NotFoundException("User not found"));
        ManageBank manageBank = ManageBank.builder()
            .quizBank(quizBank)
            .user(user)
            .editable(body.getEditable())
            .build();
        return ManageBankResponse.of(manageBankRepository.save(manageBank));
    }

    @Override
    public void deleteAllByQuizBank(Long bankId) {
        QuizBank quizBank = bankRepository.findById(bankId)
            .orElseThrow(() -> new NotFoundException("Bank not found"));
        manageBankRepository.deleteAllByQuizBank(quizBank);
    }

    @Override
    public void deleteManageBank(Long manageBankId) {
        ManageBank manageBank = manageBankRepository.findById(manageBankId)
            .orElseThrow(() -> new NotFoundException("Manage bank not found"));
        manageBankRepository.delete(manageBank);
    }
}
