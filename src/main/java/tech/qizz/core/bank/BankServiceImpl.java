package tech.qizz.core.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tech.qizz.core.bank.dto.*;
import tech.qizz.core.entity.*;
import tech.qizz.core.exception.ForbiddenException;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.manageBank.ManageBankRepository;
import tech.qizz.core.manageBank.dto.CreateManageBankRequest;
import tech.qizz.core.manageCategory.CategoryRepository;
import tech.qizz.core.manageCategory.dto.CategoryResponse;
import tech.qizz.core.manageSubCategory.SubCategoryRepository;
import tech.qizz.core.manageUser.UserRepository;

@AllArgsConstructor
@Service
public class BankServiceImpl implements BankService {

    private BankRepository bankRepository;
    private UserRepository userRepository;
    private ManageBankRepository manageBankRepository;
    private SubCategoryRepository subCategoryRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public BankResponse getBankResponseById(Long id, User user) {
        Optional<QuizBank> bank = bankRepository.findById(id);
        BankResponse bankResponse = bank.map(BankResponse::of)
                .orElseThrow(() -> new NotFoundException("Bank not found"));
        boolean kt=false;
        if (bankResponse.getQuizPublicity()) {
            kt=true;
        }
        if (bankResponse.getManageBanks().stream().map(manageBankResponse -> manageBankResponse.getUser().getId()).toList().contains(user.getUserId())) {
            kt=true;
        }
        if (!kt) {
            throw new ForbiddenException("You don't have permission to access this bank");
        }
        return bankResponse;
    }

    public BankResponse saveBank(CreateBankRequest bank, User user) {
        //check if bank exists
        bank.getManageBanks().stream().map(
            (manageBank) -> userRepository.findByEmail(manageBank.getEmail()).orElseThrow(
                () -> new NotFoundException("Email " + manageBank.getEmail() + " not found")));


        QuizBank savedBank = QuizBank.builder()
            .name(bank.getName())
            .description(bank.getDescription())
            .featuresImage(bank.getFeaturesImage())
            .quizPublicity(bank.getQuizPublicity())
            .publicEditable(bank.getPublicEditable())
            .draft(bank.getDraft())
            .createdBy(user)
            .modifiedBy(user)
//                .subCategories(null)
//            .subCategories((Set<SubCategory>) bank.getSubCategories().stream().map((subCategory) -> subCategoryRepository.findById(subCategory)))
            .build();

//        boolean kt=false;
//        if (savedBank.getQuizPublicity() && savedBank.getPublicEditable()) {
//            kt=true;
//        }
//        for (ManageBank manageBank : savedBank.getManageBanks()) {
//            if (manageBank.getUser().getUserId()==user.getUserId() && manageBank.getEditable()) {
//                kt=true;
//                break;
//            }
//        }
//        if (!kt) {
//            throw new ForbiddenException("You don't have permission to edit this bank");
//        }

        QuizBank newBank = bankRepository.save(savedBank);
        List<ManageBank> manageBankss = new ArrayList<ManageBank>();
        for (CreateManageBankRequest manageBank : bank.getManageBanks()) {
            manageBankss.add(manageBankRepository.save(ManageBank.builder()
                .quizBank(newBank)
                .user(userRepository.findByEmail(manageBank.getEmail()).orElseThrow(
                    () -> new NotFoundException("Email " + manageBank.getEmail() + " not found")))
                .editable(manageBank.getEditable())
                .build()));
        }
        ;
        newBank.setManageBanks(manageBankss);

        return BankResponse.of(newBank);
    }

    @Override
    public BankResponse updateBank(Long id, UpdateBankRequest bank, User user) {
        QuizBank oldBank = bankRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Bank not found"));
        boolean kt=false;
        if (oldBank.getQuizPublicity() && oldBank.getPublicEditable()) {
            kt=true;
        }
        for (ManageBank manageBank : oldBank.getManageBanks()) {
            if (manageBank.getUser().getUserId()==user.getUserId() && manageBank.getEditable()) {
                kt=true;
                break;
            }
        }
        if (!kt) {
            throw new ForbiddenException("You don't have permission to edit this bank");
        }

        bank.getManageBanks().stream().map(
            (manageBank) -> userRepository.findByEmail(manageBank.getEmail()).orElseThrow(
                () -> new NotFoundException("Email " + manageBank.getEmail() + " not found")));

        manageBankRepository.deleteAllByQuizBank(oldBank);

        //set lại người chỉnh sửa

        List<ManageBank> manageBankss = new ArrayList<ManageBank>();
        for (CreateManageBankRequest manageBank : bank.getManageBanks()) {
            manageBankss.add(manageBankRepository.save(ManageBank.builder()
                .quizBank(oldBank)
                .user(userRepository.findByEmail(manageBank.getEmail()).orElseThrow(
                    () -> new NotFoundException("Email " + manageBank.getEmail() + " not found")))
                .editable(manageBank.getEditable())
                .build()));
        }
        ;
        oldBank.setManageBanks(manageBankss);
        modelMapper.map(bank, oldBank);
        oldBank.setModifiedBy(user);
        //remember to update category
        return BankResponse.of(bankRepository.save(oldBank));
    }

    //owner, staff, admin
    @Override
    public void deleteBank(Long id, User user) {
        QuizBank bank = bankRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Bank not found"));

        if (bank.getCreatedBy().getUserId()!=user.getUserId()) {
            throw new ForbiddenException("You don't have permission to delete this bank");
        }

        bankRepository.delete(bank);
    }

    @Override
    public BankResponse updateSubCategoryToBank(Long id, CreateSubCategoryToBankRequest subCategories, User user) {
        QuizBank bank = bankRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Bank not found"));

        boolean kt=false;
        if (bank.getQuizPublicity() && bank.getPublicEditable()) {
            kt=true;
        }
        for (ManageBank manageBank : bank.getManageBanks()) {
            if (manageBank.getUser().getUserId()==user.getUserId() && manageBank.getEditable()) {
                kt=true;
                break;
            }
        }
        if (!kt) {
            throw new ForbiddenException("You don't have permission to edit this bank");
        }

        bank.setSubCategories(subCategoryRepository.findAllById(subCategories.getSubCategories()).stream().collect(Collectors.toSet()));
        return BankResponse.of(bankRepository.save(bank));
    }

    @Override
    public BankResponse addSubCategoryToBank(Long id, CreateSubCategoryToBankRequest subCategories, User user) {
        QuizBank bank = bankRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Bank not found"));

        boolean kt=false;
        if (bank.getQuizPublicity() && bank.getPublicEditable()) {
            kt=true;
        }
        for (ManageBank manageBank : bank.getManageBanks()) {
            if (manageBank.getUser().getUserId()==user.getUserId() && manageBank.getEditable()) {
                kt=true;
                break;
            }
        }
        if (!kt) {
            throw new ForbiddenException("You don't have permission to edit this bank");
        }

//        bank.setSubCategories(subCategoryRepository.findAllById(subCategories.getSubCategories()).stream().collect(Collectors.toSet()));
        bank.getSubCategories().addAll(subCategoryRepository.findAllById(subCategories.getSubCategories()));
//        subCategories.getSubCategories().forEach((subCategory) -> bank.getSubCategories().add(subCategoryRepository.findById(subCategory).get()));
        return BankResponse.of(bankRepository.save(bank));
    }

    @Override

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
    ) {
        Sort sortType = sort.equalsIgnoreCase("asc") ? Sort.by(order) : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page - 1, limit, sortType);
        Page<QuizBank> banks = bankRepository.findBanks(keyword, draft, (subCategoryIds==null||subCategoryIds.isEmpty())?null:subCategoryIds, (subCategoryIds==null||subCategoryIds.isEmpty())?0:subCategoryIds.size(),tab, user,pageable);
        return GetAllBanksResponse.of(banks);
    }

    @Override
    public List<BanksByCategoryResponse> getListBanksByCategories(Long top) {
        List<Category> categories = categoryRepository.findAll();
        List<BanksByCategoryResponse> banksByCategoryResponses = new ArrayList<>();
        categories.forEach((category) -> {
            List<QuizBank> banks = bankRepository.findTop5ByCategoryOrderByUpvotesDesc(category, PageRequest.of(0, top.intValue()));
            banksByCategoryResponses.add(new BanksByCategoryResponse(CategoryResponse.of(category), banks.stream().map(BankResponse::of).toList()));
        });
        return banksByCategoryResponses;
    }

    @Override
    public UpvoteResponse isUpvoted(Long id, User user) {
        QuizBank bank = bankRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Bank not found"));
        if (bank.getUpVoteUsers().stream().anyMatch(u -> u.getUserId() == user.getUserId())) {
            return new UpvoteResponse(true);
        }
        return new UpvoteResponse(false);
    }

    @Override
    public UpvoteResponse updateUpvote(Long id, User user) {
        QuizBank bank = bankRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Bank not found"));
        User u = userRepository.findById(user.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));
        if (bank.getUpVoteUsers().contains(u)) {
            bank.getUpVoteUsers().remove(u);
            bankRepository.save(bank);
            return new UpvoteResponse(false);
        }
        bank.getUpVoteUsers().add(u);
        bankRepository.save(bank);
        return new UpvoteResponse(true);
    }

    @Override
    public FavoriteResponse isFavorite(Long id, User user) {
        QuizBank bank = bankRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bank not found"));
        if (bank.getFavoriteUsers().stream().anyMatch(u -> u.getUserId() == user.getUserId())) {
            return new FavoriteResponse(true);
        }
        return new FavoriteResponse(false);
    }

    @Override
    public FavoriteResponse updateFavorite(Long id, User user) {
        QuizBank bank = bankRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bank not found"));
        User u = userRepository.findById(user.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));
        if (bank.getFavoriteUsers().contains(u)) {
            bank.getFavoriteUsers().remove(u);
            bankRepository.save(bank);
            return new FavoriteResponse(false);
        }
        bank.getFavoriteUsers().add(u);
        bankRepository.save(bank);
        return new FavoriteResponse(true);
    }
}
