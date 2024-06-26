package tech.qizz.core.module.manageBank;

import org.springframework.stereotype.Service;
import tech.qizz.core.module.manageBank.dto.CreateManageBankRequest;
import tech.qizz.core.module.manageBank.dto.ManageBankResponse;

import java.util.List;

@Service
public interface ManageBankService {

    public List<ManageBankResponse> getAllManageBanksByBankId(Long bankId);

    public ManageBankResponse createManageBank(Long bankId, CreateManageBankRequest body);

    public void deleteAllByQuizBank(Long bankId);

    public void deleteManageBank(Long manageBankId);
}
