package tech.qizz.core.module.bank;

import tech.qizz.core.bank.dto.*;
import tech.qizz.core.entity.User;

import java.util.List;
import tech.qizz.core.module.bank.dto.BankResponse;
import tech.qizz.core.module.bank.dto.BanksByCategoryResponse;
import tech.qizz.core.module.bank.dto.CreateBankRequest;
import tech.qizz.core.module.bank.dto.CreateSubCategoryToBankRequest;
import tech.qizz.core.module.bank.dto.FavoriteResponse;
import tech.qizz.core.module.bank.dto.GetAllBanksResponse;
import tech.qizz.core.module.bank.dto.UpdateBankRequest;
import tech.qizz.core.module.bank.dto.UpvoteResponse;

public interface BankService {
    public BankResponse getBankResponseById(Long id, User user);
    public BankResponse saveBank(CreateBankRequest bank, User user);
    public BankResponse updateBank(Long id, UpdateBankRequest bank, User user);

    public void deleteBank(Long id, User user);

    public BankResponse updateSubCategoryToBank(Long id, CreateSubCategoryToBankRequest subCategories, User user);

    public BankResponse addSubCategoryToBank(Long id, CreateSubCategoryToBankRequest subCategories, User user);

    public GetAllBanksResponse getAllBanks(
        Integer page,
        Integer limit,
        String keyword,
        String order,
        String sort,
        List<Long> subCategoryIds,
        String tab,
        Boolean draft,
        User user
    );

    public List<BanksByCategoryResponse> getListBanksByCategories(Long top);

    public UpvoteResponse isUpvoted(Long id, User user);

    public UpvoteResponse updateUpvote(Long id, User user);

    public FavoriteResponse isFavorite(Long id, User user);

    public FavoriteResponse updateFavorite(Long id, User user);

    public BankResponse duplicateBank(Long id, User user);
}
