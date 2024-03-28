package tech.qizz.core.manageBank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.qizz.core.repository.ManageBankRepository;
import tech.qizz.core.module.manageBank.ManageBankServiceImpl;
import tech.qizz.core.repository.BankRepository;
import tech.qizz.core.entity.ManageBank;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.module.manageBank.dto.ManageBankResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ManageBankServiceImplTest {
    @InjectMocks
    private ManageBankServiceImpl manageBankService;

    @Mock
    private ManageBankRepository manageBankRepository;

    @Mock
    private BankRepository bankRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testGetAllManageBanksByBankId() {
//        //Mock data
//        Long bankId = 1L;
//        QuizBank mockedQuizBank = new QuizBank();
//        when(bankRepository.findById(bankId)).thenReturn(Optional.of(mockedQuizBank));
//
//        List<ManageBank> mockedManageBanks = new ArrayList<>();
//
//        //Mock repository method
//        when(manageBankRepository.findAllByQuizBank(mockedQuizBank)).thenReturn(mockedManageBanks);
//
//        //Call the service method
//        List<ManageBankResponse> response = manageBankService.getAllManageBanksByBankId(bankId);
//
//        //Assertions
//        assertEquals(mockedManageBanks, response);
//
//    }
@Test
void testGetAllManageBanksByBankId() {
    //Mock data
    Long bankId = -5L;
    QuizBank mockedQuizBank = new QuizBank();
    when(bankRepository.findById(bankId)).thenReturn(Optional.of(mockedQuizBank));

    List<ManageBank> mockedManageBanks = new ArrayList<>();

    //Mock repository method
    when(manageBankRepository.findAllByQuizBank(mockedQuizBank)).thenReturn(mockedManageBanks);

    //Call the service method
    List<ManageBankResponse> response = manageBankService.getAllManageBanksByBankId(bankId);

    //Assertions
    assertEquals(mockedManageBanks, response);

}




}
