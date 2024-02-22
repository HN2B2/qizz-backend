package tech.qizz.core.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import tech.qizz.core.bank.dto.BankResponse;
import tech.qizz.core.bank.dto.CreateBankRequest;
import tech.qizz.core.bank.dto.CreateSubCategoryToBankRequest;
import tech.qizz.core.bank.dto.UpdateBankRequest;
import tech.qizz.core.entity.ManageBank;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.entity.SubCategory;
import tech.qizz.core.entity.User;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.manageBank.ManageBankRepository;
import tech.qizz.core.manageBank.dto.CreateManageBankRequest;
import tech.qizz.core.manageCategory.CategoryRepository;
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

    @Override
    public BankResponse getBankResponseById(Long id) {
        Optional<QuizBank> bank = bankRepository.findById(id);
        return bank.map(BankResponse::of)
            .orElseThrow(() -> new NotFoundException("Bank not found"));
    }

    public BankResponse saveBank(CreateBankRequest bank, User user) {
//        bank.getManageUsers().stream().map
//        Optional<ManageBank> manageBank = manageBankRepository.save()

//        Set<User> manageUsers = bank
//                .getManageUsers()
//                .stream()
//                .map((manageUser) -> userRepository.findByEmail(manageUser.getEmail())
//                        .orElseThrow(() -> new NotFoundException("Email " + manageUser.getEmail() + " not found")))
//                .collect(Collectors.toSet());
        bank.getManageBanks().stream().map(
            (manageBank) -> userRepository.findByEmail(manageBank.getEmail()).orElseThrow(
                () -> new NotFoundException("Email " + manageBank.getEmail() + " not found")));

//        bank.getSubCategories().stream().map(
//            (subCategory) -> subCategoryRepository.findById(subCategory).orElseThrow(
//                () -> new NotFoundException("Category " + subCategory + " not found"))
//        );
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
//        System.out.println(newBank.getManageBanks());

        return BankResponse.of(newBank);
    }

    @Override
    public BankResponse updateBank(Long id, UpdateBankRequest bank, User user) {
        QuizBank oldBank = bankRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Bank not found"));

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

    @Override
    public void deleteBank(Long id, User user) {
        QuizBank bank = bankRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Bank not found"));
        bankRepository.delete(bank);
    }

    @Override
    public BankResponse updateSubCategoryToBank(Long id, CreateSubCategoryToBankRequest subCategories, User user) {
        QuizBank bank = bankRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Bank not found"));
        bank.setSubCategories(subCategoryRepository.findAllById(subCategories.getSubCategories()).stream().collect(Collectors.toSet()));
        return BankResponse.of(bankRepository.save(bank));
    }

    @Override
    public BankResponse addSubCategoryToBank(Long id, CreateSubCategoryToBankRequest subCategories, User user) {
        QuizBank bank = bankRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Bank not found"));
//        bank.setSubCategories(subCategoryRepository.findAllById(subCategories.getSubCategories()).stream().collect(Collectors.toSet()));
        bank.getSubCategories().addAll(subCategoryRepository.findAllById(subCategories.getSubCategories()));
//        subCategories.getSubCategories().forEach((subCategory) -> bank.getSubCategories().add(subCategoryRepository.findById(subCategory).get()));
        return BankResponse.of(bankRepository.save(bank));
    }
}
