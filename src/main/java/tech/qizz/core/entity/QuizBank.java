package tech.qizz.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quiz_banks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_bank_id")
    private long quizBankId;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Lob
    @Column(name = "features_image", nullable = false)
    private String featuresImage;

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

    @Column(name = "quiz_publicity")
    private Boolean quizPublicity;

    @Column(name = "public_editable")
    private Boolean publicEditable;

    @Column(name="draft")
    private Boolean draft;

    @JsonIgnore
    @OneToMany(mappedBy = "quizBank", cascade = CascadeType.ALL)
    private List<Quiz> quizzes;

    @JsonIgnore
    @OneToMany(mappedBy = "quizBank", cascade = CascadeType.ALL)
    private List<Question> questions;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private User createdBy;

    @ManyToOne(optional = false)
    @JoinColumn(name = "modified_by", referencedColumnName = "user_id")
    private User modifiedBy;

    @OneToMany(mappedBy = "quizBank",cascade = CascadeType.ALL)
    private List<QuizSubCategory> quizSubCategories;

    @OneToMany(mappedBy = "quizBank", cascade = CascadeType.ALL)
    private List<FavoriteBank> favoriteBanks;

    @OneToMany(mappedBy = "quizBank", cascade = CascadeType.ALL)
    private List<ManageBank> manageBanks;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "upvote_banks",
        joinColumns = @JoinColumn(name = "bank_id", referencedColumnName = "quiz_bank_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    )
    private Set<User> upVoteUsers;



}
