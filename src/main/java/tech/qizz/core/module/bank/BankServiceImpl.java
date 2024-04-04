package tech.qizz.core.module.bank;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
//import tech.qizz.core.bank.dto.*;
import tech.qizz.core.entity.*;
import tech.qizz.core.entity.constant.UserRole;
import tech.qizz.core.exception.ForbiddenException;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.module.bank.dto.*;
import tech.qizz.core.repository.ManageBankRepository;
import tech.qizz.core.module.manageBank.dto.CreateManageBankRequest;
import tech.qizz.core.repository.CategoryRepository;
import tech.qizz.core.module.manageCategory.dto.CategoryResponse;
import tech.qizz.core.repository.SubCategoryRepository;
import tech.qizz.core.repository.UserRepository;
import tech.qizz.core.repository.QuestionRepository;
import tech.qizz.core.repository.BankRepository;

@AllArgsConstructor
@Service
public class BankServiceImpl implements BankService {

    private BankRepository bankRepository;
    private UserRepository userRepository;
    private ManageBankRepository manageBankRepository;
    private SubCategoryRepository subCategoryRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

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
        if (user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.STAFF)) {
            kt=true;
        }
        if (bankResponse.getCreatedBy().getId().equals(user.getUserId())) {
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
                .disabled(false)
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
        if (user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.STAFF)) {
            kt=true;
        }
        if (oldBank.getCreatedBy().getUserId()==user.getUserId()) {
            kt=true;
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

        if (user.getRole() != UserRole.ADMIN && user.getRole() != UserRole.STAFF && bank.getCreatedBy().getUserId()!=user.getUserId()) {
            throw new ForbiddenException("You don't have permission to delete this bank");
        }

        bank.setDisabled(true);
        bankRepository.save(bank);
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
        if (user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.STAFF)) {
            kt=true;
        }
        if (bank.getCreatedBy().getUserId()==user.getUserId()) {
            kt=true;
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
        if (user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.STAFF)) {
            kt=true;
        }
        if (bank.getCreatedBy().getUserId()==user.getUserId()) {
            kt=true;
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

    @Override
    public BankResponse duplicateBank(Long id, User user) {
        QuizBank bank = bankRepository.findById(id).orElseThrow(() -> new NotFoundException("Bank not found"));

        boolean kt=false;
        if (bank.getQuizPublicity()) {
            kt=true;
        }
        if (bank.getManageBanks().stream().map(manageBankResponse -> manageBankResponse.getUser().getUserId()).toList().contains(user.getUserId())) {
            kt=true;
        }
        if (user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.STAFF)) {
            kt=true;
        }
        if (bank.getCreatedBy().getUserId()==user.getUserId()) {
            kt=true;
        }
        if (!kt) {
            throw new ForbiddenException("You don't have permission to duplicate this bank");
        }
        //duplicate this bank
        QuizBank newBank = new QuizBank();
        newBank.setName(bank.getName());
        newBank.setDescription(bank.getDescription());
        newBank.setSubCategories(new HashSet<>(bank.getSubCategories()));

        newBank.setQuizPublicity(true);
        List<ManageBank> manageBankss = new ArrayList<ManageBank>();
        newBank.setManageBanks(manageBankss);
        newBank.setPublicEditable(true);
        newBank.setDraft(true);
        newBank.setCreatedBy(user);
        newBank.setModifiedBy(user);
        newBank.setDraft(false);
        newBank.setDisabled(false);

        // Save the duplicated bank to get its ID
        newBank = bankRepository.save(newBank);

        List<Question> originalQuestions = bank.getQuestions();
        List<Question> newQuestions = new ArrayList<>();

        for (Question originalQuestion : originalQuestions) {
            Question newQuestion = new Question();
            newQuestion.setQuestionIndex(originalQuestion.getQuestionIndex());
            newQuestion.setDuration(originalQuestion.getDuration());
            newQuestion.setDisabled(originalQuestion.getDisabled());
            newQuestion.setContent(originalQuestion.getContent());
            newQuestion.setAnswersMetadata(originalQuestion.getAnswersMetadata());
            newQuestion.setCorrectAnswersMetadata(originalQuestion.getCorrectAnswersMetadata());
            newQuestion.setExplainAnswer(originalQuestion.getExplainAnswer());
            newQuestion.setPoint(originalQuestion.getPoint());
            newQuestion.setType(originalQuestion.getType());
            newQuestion.setQuizBank(newBank);

            newQuestions.add(newQuestion);
        }

// Associate the duplicated questions with the new bank
//        for (Question newQuestion : newQuestions) {
//            newQuestion.setQuizBank(newBank);
//        }

// Save the duplicated questions
        newQuestions = questionRepository.saveAll(newQuestions);

// Set the duplicated questions to the new bank
        newBank.setQuestions(newQuestions);

// Save the updated bank with duplicated questions
        newBank = bankRepository.save(newBank);

        return BankResponse.of(bankRepository.save(newBank));
    }

    @Override
    public GetAllBanksResponse getAllBanks(
            Integer page,
            Integer limit,
            String keyword,
            String order,
            String sort,
            List<Long> subCategoryIds,
            Integer mi,
            Integer ma,
            User user) {
        Sort sortType = sort.equalsIgnoreCase("asc") ? Sort.by(order) : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page - 1, limit, sortType);
        Page<QuizBank> banks = bankRepository.findBanksByKeyword(keyword, (subCategoryIds==null||subCategoryIds.isEmpty())?null:subCategoryIds, (subCategoryIds==null||subCategoryIds.isEmpty())?0:subCategoryIds.size(), mi, ma, user, pageable);
        return GetAllBanksResponse.of(banks);
    }
}
