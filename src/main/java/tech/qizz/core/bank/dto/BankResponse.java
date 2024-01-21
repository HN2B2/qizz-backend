package tech.qizz.core.bank.dto;

import lombok.*;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.entity.SubCategory;
import tech.qizz.core.entity.User;

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
    private User createdBy;
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
                .totalQuestions(bank.getQuestions().size())
                .createdBy(bank.getCreatedBy())
                .totalUpVotes(bank.getUpVoteUsers().size())
                .build();
    }


}
