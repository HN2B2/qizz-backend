package tech.qizz.core.manageBank.dto;

import lombok.*;
import tech.qizz.core.entity.ManageBank;
import tech.qizz.core.user.dto.UserResponse;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManageBankResponse {
    private long manageBankId;
    private Date createdAt;
    private Date modifiedAt;
    private UserResponse user;
    private Boolean editable;

    public static ManageBankResponse of(ManageBank manageBank) {
        return ManageBankResponse.builder()
                .manageBankId(manageBank.getManageBankId())
                .createdAt(manageBank.getCreatedAt())
                .modifiedAt(manageBank.getModifiedAt())
                .user(UserResponse.of(manageBank.getUser()))
                .editable(manageBank.getEditable())
                .build();
    }
}
