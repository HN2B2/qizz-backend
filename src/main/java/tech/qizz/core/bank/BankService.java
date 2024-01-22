package tech.qizz.core.bank;

import tech.qizz.core.bank.dto.BankRequest;
import tech.qizz.core.bank.dto.BankResponse;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.entity.User;

public interface BankService {
    public BankResponse getBankResponseById(Long id);
    public BankResponse saveBank(BankRequest bank, User user);
}
