package tech.qizz.core.bank;

import tech.qizz.core.bank.dto.BankRequest;
import tech.qizz.core.bank.dto.BankResponse;
import tech.qizz.core.entity.QuizBank;

public interface BankService {
    public BankResponse getBankResponseById(Long id);
    public BankResponse saveBank(QuizBank bank);
}
