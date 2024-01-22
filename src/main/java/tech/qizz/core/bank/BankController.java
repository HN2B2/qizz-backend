package tech.qizz.core.bank;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.qizz.core.annotation.RequestUser;
import tech.qizz.core.bank.dto.BankRequest;
import tech.qizz.core.bank.dto.BankResponse;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.entity.User;
import tech.qizz.core.user.UserRepository;
import tech.qizz.core.user.UserService;
import tech.qizz.core.user.dto.UserResponse;

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

    @PostMapping()
    public ResponseEntity<BankResponse> saveBankRequest(@RequestBody BankRequest bank, @RequestUser User user) {

        BankResponse savedBank = bankService.saveBank(bank, user);
        return new ResponseEntity<>(savedBank, HttpStatus.CREATED);
    }

}
