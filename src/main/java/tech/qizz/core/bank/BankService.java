package tech.qizz.core.bank;

import tech.qizz.core.bank.dto.CreateBankRequest;
import tech.qizz.core.bank.dto.BankResponse;
import tech.qizz.core.bank.dto.UpdateBankRequest;
import tech.qizz.core.entity.User;

public interface BankService {
    public BankResponse getBankResponseById(Long id);
    public BankResponse saveBank(CreateBankRequest bank, User user);
    public BankResponse updateBank(Long id, UpdateBankRequest bank, User user);

    public void deleteBank(Long id, User user);
}
