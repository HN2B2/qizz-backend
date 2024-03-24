package tech.qizz.core.module.manageBank;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.qizz.core.module.manageBank.dto.CreateManageBankRequest;
import tech.qizz.core.module.manageBank.dto.ManageBankResponse;

import java.util.List;

@RestController
@Controller
@AllArgsConstructor
@RequestMapping("/manageBank")
public class ManageBankController {
    private ManageBankService manageBankService;
    @GetMapping("all/bankId/{bankId}")
    public ResponseEntity<List<ManageBankResponse>> getAllManageBanksByBankId(@PathVariable Long bankId){
        List<ManageBankResponse> manageBanks = manageBankService.getAllManageBanksByBankId(bankId);
        return new ResponseEntity<>(manageBanks, HttpStatus.OK);
    }

    @PostMapping("/bankId/{bankId}")
    public ResponseEntity<ManageBankResponse> createManageBank(@PathVariable Long bankId, @RequestBody CreateManageBankRequest body){
        ManageBankResponse manageBank = manageBankService.createManageBank(bankId, body);
        return new ResponseEntity<>(manageBank, HttpStatus.CREATED);
    }

    @DeleteMapping("/bankId/{bankId}")
    public ResponseEntity<HttpStatus> deleteManageBanks(@PathVariable Long bankId){
        manageBankService.deleteAllByQuizBank(bankId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{manageBankId}")
    public ResponseEntity<HttpStatus> deleteManageBank(@PathVariable Long manageBankId){
        manageBankService.deleteManageBank(manageBankId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
