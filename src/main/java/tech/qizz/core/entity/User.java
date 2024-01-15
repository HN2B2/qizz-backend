package tech.qizz.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
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
    private String role;

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
    @OneToMany(mappedBy = "receiver")
    private List<UserNotification> receiverNotifications;

    @JsonIgnore
    @OneToMany(mappedBy = "sender")
    private List<UserNotification> senderNotifications;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy")
    private List<Quiz> quizzes;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy")
    private List<QuizBank> creatorQuizBanks;

    @JsonIgnore
    @OneToMany(mappedBy = "modifiedBy")
    private List<QuizBank> modifierQuizBanks;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<QuizJoinedUser> quizJoinedUsers;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<QuizBank> intermediateFavoriteBanks;

    @ManyToMany(mappedBy = "manageUsers", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<QuizBank> intermediateManageQuizBanks;
}
