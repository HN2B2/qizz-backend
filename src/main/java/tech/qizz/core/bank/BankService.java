package tech.qizz.core.bank;

import tech.qizz.core.bank.dto.CreateBankRequest;
import tech.qizz.core.bank.dto.BankResponse;
import tech.qizz.core.bank.dto.CreateSubCategoryToBankRequest;
import tech.qizz.core.bank.dto.UpdateBankRequest;
import tech.qizz.core.entity.User;
import tech.qizz.core.manageSubCategory.dto.CreateSubCategoryRequest;

import java.util.List;

public interface BankService {
    public BankResponse getBankResponseById(Long id);
    public BankResponse saveBank(CreateBankRequest bank, User user);
    public BankResponse updateBank(Long id, UpdateBankRequest bank, User user);

    public void deleteBank(Long id, User user);

    public BankResponse updateSubCategoryToBank(Long id, CreateSubCategoryToBankRequest subCategories, User user);

    public BankResponse addSubCategoryToBank(Long id, CreateSubCategoryToBankRequest subCategories, User user);
}
