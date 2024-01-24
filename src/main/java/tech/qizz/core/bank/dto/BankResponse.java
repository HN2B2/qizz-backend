package tech.qizz.core.bank.dto;

import lombok.*;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.entity.SubCategory;
import tech.qizz.core.entity.User;
import tech.qizz.core.user.dto.UserResponse;

import java.util.Date;
import java.util.Set;

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
    private Set<SubCategory> subCategories;
    private Boolean draft;
    private Integer totalQuestions;
    private UserResponse createdBy;
    private UserResponse modifiedBy;
    private Integer totalUpVotes;

    public static BankResponse of (QuizBank bank) {
        return BankResponse.builder()
                .quizBankId(bank.getQuizBankId())
                .name(bank.getName())
                .description(bank.getDescription())
                .featuresImage(bank.getFeaturesImage())
                .createdAt(bank.getCreatedAt())
                .modifiedAt(bank.getModifiedAt())
                .quizPublicity(bank.getQuizPublicity())
                .publicEditable(bank.getPublicEditable())
                .subCategories(bank.getSubCategories())
                .draft(bank.getDraft())
                .totalQuestions((bank.getQuestions()==null)?0:bank.getQuestions().size())
                .createdBy(UserResponse.of(bank.getCreatedBy()))
                .modifiedBy(UserResponse.of(bank.getModifiedBy()))
                .totalUpVotes((bank.getUpVoteUsers()==null)?0:bank.getUpVoteUsers().size())
                .build();
    }


}
