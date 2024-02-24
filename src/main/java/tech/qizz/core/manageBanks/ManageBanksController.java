package tech.qizz.core.manageBanks;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.qizz.core.manageBanks.dto.BankResponse;
import tech.qizz.core.manageBanks.dto.GetAllBanksResponse;

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
            @RequestParam(required = false, defaultValue = "desc") String sort
    ) {
        GetAllBanksResponse banks = manageBanksService.getAllBanks(
                page,
                limit,
                keyword,
                order,
                sort
        );
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'STAFF', 'ADMIN')")
    public ResponseEntity<BankResponse> getBankById(@PathVariable Long id) {
        BankResponse bank = manageBanksService.getBankById(id);
        return new ResponseEntity<>(bank, HttpStatus.OK);
    }
}
