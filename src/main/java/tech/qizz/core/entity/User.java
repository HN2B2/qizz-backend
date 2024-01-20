package tech.qizz.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.qizz.core.user.constant.UserRole;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Lob
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        modifiedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = new Date();
    }

    @Column(name = "banned")
    private Boolean banned;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<FeedBack> feedbacks;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserMetadata> userMetadatas;

    @JsonIgnore
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<UserNotification> receiverNotifications;

    @JsonIgnore
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<UserNotification> senderNotifications;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Quiz> quizzes;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<QuizBank> creatorQuizBanks;

    @JsonIgnore
    @OneToMany(mappedBy = "modifiedBy", cascade = CascadeType.ALL)
    private List<QuizBank> modifierQuizBanks;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<QuizJoinedUser> quizJoinedUsers;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<QuizBank> intermediateFavoriteBanks;

    @ManyToMany(mappedBy = "manageUsers", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<QuizBank> intermediateManageQuizBanks;

    @ManyToMany(mappedBy = "upVoteUsers", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<QuizBank> intermediateUpvotedQuizBanks;
}
