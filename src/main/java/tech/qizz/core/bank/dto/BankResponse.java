package tech.qizz.core.bank.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.entity.SubCategory;
import tech.qizz.core.manageBank.dto.ManageBankResponse;
import tech.qizz.core.user.dto.ProfileResponse;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class BankResponse {

    private long quizBankId;
    private String name;
    private String description;
    private String featuresImage;
    private Date createdAt;
    private Date modifiedAt;
    private Boolean quizPublicity;
    private Boolean publicEditable;
    private List<SubCategory> subCategories;
    private Boolean draft;
    private Integer totalQuestions;
    private ProfileResponse createdBy;
    private ProfileResponse modifiedBy;
    private List<ManageBankResponse> manageBanks;
    private Integer totalUpVotes;

    public static BankResponse of(QuizBank bank) {
        return BankResponse.builder()
            .quizBankId(bank.getQuizBankId())
            .name(bank.getName())
            .description(bank.getDescription())
            .featuresImage(bank.getFeaturesImage())
            .createdAt(bank.getCreatedAt())
            .modifiedAt(bank.getModifiedAt())
            .quizPublicity(bank.getQuizPublicity())
            .publicEditable(bank.getPublicEditable())
            .subCategories(null)
            .draft(bank.getDraft())
            .totalQuestions((bank.getQuestions() == null) ? 0 : bank.getQuestions().size())
            .createdBy(ProfileResponse.of(bank.getCreatedBy()))
            .modifiedBy(ProfileResponse.of(bank.getModifiedBy()))
            .manageBanks(bank.getManageBanks().stream().map(ManageBankResponse::of)
                .collect(Collectors.toList()))
            .totalUpVotes((bank.getUpVoteUsers() == null) ? 0 : bank.getUpVoteUsers().size())
            .build();
    }


}
