package tech.qizz.core.bank;

import tech.qizz.core.bank.dto.CreateBankRequest;
import tech.qizz.core.bank.dto.BankResponse;
import tech.qizz.core.entity.User;

public interface BankService {
    public BankResponse getBankResponseById(Long id);
    public BankResponse saveBank(CreateBankRequest bank, User user);
}
