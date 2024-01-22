package tech.qizz.core.bank.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.entity.SubCategory;
import tech.qizz.core.entity.User;
import tech.qizz.core.user.UserRepository;
import tech.qizz.core.user.UserService;
import tech.qizz.core.user.dto.UserResponse;

import java.util.Date;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankRequest {
    private String name;
    private String description;
    private String featuresImage;
    private Boolean quizPublicity;
    private Boolean publicEditable;
    private Boolean draft;
}
