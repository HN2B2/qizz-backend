package tech.qizz.core.module.manageBank.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.qizz.core.entity.ManageBank;
import tech.qizz.core.module.user.dto.ProfileResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManageBankResponse {

    private long manageBankId;
    private Date createdAt;
    private Date modifiedAt;
    private ProfileResponse user;
    private Boolean editable;

    public static ManageBankResponse of(ManageBank manageBank) {
        return ManageBankResponse.builder()
            .manageBankId(manageBank.getManageBankId())
            .createdAt(manageBank.getCreatedAt())
            .modifiedAt(manageBank.getModifiedAt())
            .user(ProfileResponse.of(manageBank.getUser()))
            .editable(manageBank.getEditable())
            .build();
    }
}
