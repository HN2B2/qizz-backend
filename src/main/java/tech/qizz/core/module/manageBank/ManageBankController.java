package tech.qizz.core.module.manageBank;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.qizz.core.module.manageBank.dto.CreateManageBankRequest;
import tech.qizz.core.module.manageBank.dto.ManageBankResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/manageBank")
public class ManageBankController {

    private ManageBankService manageBankService;

    @GetMapping("all/bankId/{bankId}")
    public ResponseEntity<List<ManageBankResponse>> getAllManageBanksByBankId(
        @PathVariable Long bankId) {
        List<ManageBankResponse> manageBanks = manageBankService.getAllManageBanksByBankId(bankId);
        return new ResponseEntity<>(manageBanks, HttpStatus.OK);
    }

    @PostMapping("/bankId/{bankId}")
    public ResponseEntity<ManageBankResponse> createManageBank(@PathVariable Long bankId,
        @RequestBody CreateManageBankRequest body) {
        ManageBankResponse manageBank = manageBankService.createManageBank(bankId, body);
        return new ResponseEntity<>(manageBank, HttpStatus.CREATED);
    }

    @DeleteMapping("/bankId/{bankId}")
    public ResponseEntity<HttpStatus> deleteManageBanks(@PathVariable Long bankId) {
        manageBankService.deleteAllByQuizBank(bankId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{manageBankId}")
    public ResponseEntity<HttpStatus> deleteManageBank(@PathVariable Long manageBankId) {
        manageBankService.deleteManageBank(manageBankId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
