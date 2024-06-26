package tech.qizz.core.module.bank;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.qizz.core.annotation.RequestUser;
import tech.qizz.core.entity.User;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.module.bank.dto.*;

@RestController
@AllArgsConstructor
@RequestMapping("/bank")
public class BankController {

    private BankService bankService;

    @GetMapping("/{id}")
    public ResponseEntity<BankResponse> getBankResponseById(@PathVariable Long id,
        @RequestUser User user) {
        BankResponse bank = bankService.getBankResponseById(id, user);
        return new ResponseEntity<>(bank, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<GetAllBanksResponse> getAllBanks(
        @RequestParam(required = false, defaultValue = "1") Integer page,
        @RequestParam(required = false, defaultValue = "10") Integer limit,
        @RequestParam(required = false, defaultValue = "") String keyword,
        @RequestParam(required = false, defaultValue = "id") String order,
        @RequestParam(required = false, defaultValue = "desc") String sort,
        @RequestParam(required = false) List<Long> subCategoryIds,
        @RequestParam(required = false, defaultValue = "created") String tab,
        @RequestParam(required = false, defaultValue = "false") Boolean draft,
        @RequestUser User user
    ) {
        GetAllBanksResponse banks = bankService.getAllBanks(
            page,
            limit,
            keyword,
            order,
            sort,
            subCategoryIds,
            tab,
            draft,
            user
        );
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'STAFF', 'ADMIN')")
    public ResponseEntity<GetAllBanksResponse> getAllBanks(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "id") String order,
            @RequestParam(required = false, defaultValue = "desc") String sort,
            @RequestParam(required = false) List<Long> subCategoryIds,
            @RequestParam(required = false) Integer mi,
            @RequestParam(required = false) Integer ma,
            @RequestUser User user
    ) {
        GetAllBanksResponse banks = bankService.getAllBanks(
                page,
                limit,
                keyword,
                order,
                sort,
                subCategoryIds,
                mi,
                ma,
                user
        );
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<BankResponse> saveBankRequest(@Valid @RequestBody CreateBankRequest bank,
        @RequestUser User user) {
//        System.out.println(bank.getManageBanks());
        BankResponse savedBank = bankService.saveBank(bank, user);
        return new ResponseEntity<>(savedBank, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankResponse> updateBankRequest(@PathVariable Long id,
        @Valid @RequestBody UpdateBankRequest bank, @RequestUser User user, BindingResult result) {
        if (result.hasErrors() || bank == null) {
            throw new BadRequestException("Invalid request");
        }
        BankResponse updatedBank = bankService.updateBank(id, bank, user);
        return new ResponseEntity<>(updatedBank, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBankRequest(@PathVariable Long id,
        @RequestUser User user) {
        bankService.deleteBank(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/subCategory")
    public ResponseEntity<BankResponse> updateSubCategoryToBank(@PathVariable Long id,
        @Valid @RequestBody CreateSubCategoryToBankRequest subCategories, @RequestUser User user) {
        BankResponse updatedBank = bankService.updateSubCategoryToBank(id, subCategories, user);
        return new ResponseEntity<>(updatedBank, HttpStatus.OK);
    }

    @PostMapping("/{id}/subCategory")
    public ResponseEntity<BankResponse> addSubCategoryToBank(@PathVariable Long id,
        @Valid @RequestBody CreateSubCategoryToBankRequest subCategories, @RequestUser User user) {
        BankResponse updatedBank = bankService.addSubCategoryToBank(id, subCategories, user);
        return new ResponseEntity<>(updatedBank, HttpStatus.CREATED);
    }

    @GetMapping("/all/categories/{top}")
    public ResponseEntity<List<BanksByCategoryResponse>> getListBanksByCategories(
        @PathVariable Long top) {
        List<BanksByCategoryResponse> banks = bankService.getListBanksByCategories(top);
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    @GetMapping("/upvote/{id}")
    public ResponseEntity<UpvoteResponse> isUpvoted(@PathVariable Long id, @RequestUser User user) {
        UpvoteResponse upvote = bankService.isUpvoted(id, user);
        return new ResponseEntity<>(upvote, HttpStatus.OK);
    }

    @PutMapping("/upvote/{id}")
    public ResponseEntity<UpvoteResponse> updateUpvote(@PathVariable Long id,
        @RequestUser User user) {
        UpvoteResponse upvote = bankService.updateUpvote(id, user);
        return new ResponseEntity<>(upvote, HttpStatus.OK);
    }

    @GetMapping("/favorite/{id}")
    public ResponseEntity<FavoriteResponse> isFavorite(@PathVariable Long id,
        @RequestUser User user) {
        FavoriteResponse favorite = bankService.isFavorite(id, user);
        return new ResponseEntity<>(favorite, HttpStatus.OK);
    }

    @PutMapping("/favorite/{id}")
    public ResponseEntity<FavoriteResponse> updateFavorite(@PathVariable Long id,
        @RequestUser User user) {
        FavoriteResponse favorite = bankService.updateFavorite(id, user);
        return new ResponseEntity<>(favorite, HttpStatus.OK);
    }

    @PostMapping("/duplicate/{id}")
    public ResponseEntity<BankResponse> duplicateBank(@PathVariable Long id,
        @RequestUser User user) {
        BankResponse duplicatedBank = bankService.duplicateBank(id, user);
        return new ResponseEntity<>(duplicatedBank, HttpStatus.CREATED);
    }

}
