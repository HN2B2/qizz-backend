package tech.qizz.core.module.manageBanks;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.qizz.core.annotation.RequestUser;
import tech.qizz.core.entity.User;
import tech.qizz.core.module.manageBanks.dto.BankResponse;
import tech.qizz.core.module.manageBanks.dto.GetAllBanksResponse;

import java.util.List;

@RestController
@RequestMapping("/manageBanks")
@CrossOrigin
@RequiredArgsConstructor
public class ManageBanksController {

    private final ManageBanksService manageBanksService;

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
            @RequestParam(required = false) Integer ma
    ) {
        GetAllBanksResponse banks = manageBanksService.getAllBanks(
                page,
                limit,
                keyword,
                order,
                sort,
                subCategoryIds,
                mi,
                ma
        );
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'STAFF', 'ADMIN')")
    public ResponseEntity<BankResponse> getBankById(@PathVariable Long id) {
        BankResponse bank = manageBanksService.getBankById(id);
        return new ResponseEntity<>(bank, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBankRequest(@PathVariable Long id,
                                                        @RequestUser User user) {
        manageBanksService.deleteBank(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
