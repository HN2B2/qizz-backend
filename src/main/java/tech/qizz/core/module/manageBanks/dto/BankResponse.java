package tech.qizz.core.module.manageBanks.dto;

import lombok.*;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.module.manageBank.dto.ManageBankResponse;
import tech.qizz.core.module.manageSubCategory.dto.SubCategoryResponse;
import tech.qizz.core.module.user.dto.ProfileResponse;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<SubCategoryResponse> subCategories;
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
            .subCategories(bank.getSubCategories() == null ? null : bank.getSubCategories().stream().map(SubCategoryResponse::of).collect(Collectors.toList()))
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
