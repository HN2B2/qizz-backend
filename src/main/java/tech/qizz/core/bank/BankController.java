package tech.qizz.core.bank;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tech.qizz.core.annotation.RequestUser;
import tech.qizz.core.bank.dto.*;
import tech.qizz.core.entity.User;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.user.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/bank")
public class BankController {

    private BankService bankService;
    private UserService userService;
    @GetMapping("/{id}")
    public ResponseEntity<BankResponse> getBankResponseById(@PathVariable Long id) {
        BankResponse bank = bankService.getBankResponseById(id);
        return new ResponseEntity<>(bank, HttpStatus.OK);
    }

//    @GetMapping("/all")
//    public ResponseEntity<GetAllBanksResponse> getAllBanks(
//        @RequestParam(required = false, defaultValue = "1") Integer page,
//        @RequestParam(required = false, defaultValue = "10") Integer limit,
//        @RequestParam(required = false, defaultValue = "") String keyword,
//        @RequestParam(required = false, defaultValue = "id") String order,
//        @RequestParam(required = false, defaultValue = "desc") String sort,
//        @RequestUser User user
//    ) {
//
//    }

    @PostMapping()
    public ResponseEntity<BankResponse> saveBankRequest(@Valid @RequestBody CreateBankRequest bank, @RequestUser User user) {
//        System.out.println(bank.getManageBanks());
        BankResponse savedBank = bankService.saveBank(bank, user);
        return new ResponseEntity<>(savedBank, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankResponse> updateBankRequest(@PathVariable Long id, @Valid @RequestBody UpdateBankRequest bank, @RequestUser User user, BindingResult result) {
        if (result.hasErrors() || bank == null) {
            throw new BadRequestException("Invalid request");
        }
        BankResponse updatedBank = bankService.updateBank(id, bank, user);
        return new ResponseEntity<>(updatedBank, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBankRequest(@PathVariable Long id, @RequestUser User user) {
        bankService.deleteBank(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/subCategory")
    public ResponseEntity<BankResponse> updateSubCategoryToBank(@PathVariable Long id,@Valid @RequestBody CreateSubCategoryToBankRequest subCategories, @RequestUser User user) {
        BankResponse updatedBank = bankService.updateSubCategoryToBank(id, subCategories, user);
        return new ResponseEntity<>(updatedBank,HttpStatus.OK);
    }

    @PostMapping("/{id}/subCategory")
    public ResponseEntity<BankResponse> addSubCategoryToBank(@PathVariable Long id, @Valid @RequestBody CreateSubCategoryToBankRequest subCategories, @RequestUser User user) {
        BankResponse updatedBank = bankService.addSubCategoryToBank(id, subCategories, user);
        return new ResponseEntity<>(updatedBank,HttpStatus.CREATED);
    }

}
