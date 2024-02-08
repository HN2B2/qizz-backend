package tech.qizz.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private long quizId;

    @Column(name = "mode", nullable = false)
    private String mode;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "featured_image")
    private String featuredImage;

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

    @Column(name = "publish", nullable = false)
    private String publish;

    @Column(name = "accessed_group")
    private Boolean accessedGroup;

    @Column(name = "code", nullable = false)
    private long code;

    @Column(name = "total_joined", nullable = false)
    private long totalJoined;

    @JsonIgnore
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuizSetting> quizSettings;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "quiz_bank_id")
    private QuizBank quizBank;

    @JsonIgnore
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuizJoinedUser> quizJoinedUsers;

    @JsonIgnore
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuizQuestion> quizQuestions;

}
