package tech.qizz.core.bank;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<BankRequest> saveBankRequest(@RequestBody BankRequest bank) {
        UserResponse user = userService.getUserById(1L);
        QuizBank savedBank = bankService.saveBank(bank.to());
    }

}
