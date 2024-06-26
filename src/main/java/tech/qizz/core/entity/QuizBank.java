package tech.qizz.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "features_image")
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

    @Column(name = "draft", columnDefinition = "bit")
    private Boolean draft;


    @Column(name = "disabled", columnDefinition = "bit")
    private Boolean disabled;

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

    //    @OneToMany(mappedBy = "quizBank",cascade = CascadeType.ALL)
//    private List<QuizSubCategory> quizSubCategories;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "quiz_sub_categories",
        joinColumns = @JoinColumn(name = "bank_id", referencedColumnName = "quiz_bank_id"),
        inverseJoinColumns = @JoinColumn(name = "sub_category_id", referencedColumnName = "sub_category_id")
    )
    private Set<SubCategory> subCategories;

//    @JsonIgnore
//    @OneToMany(mappedBy = "quizBank", cascade = CascadeType.ALL)
//    private List<FavoriteBank> favoriteBanks;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "favorite_banks",
            joinColumns = @JoinColumn(name = "bank_id", referencedColumnName = "quiz_bank_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    )
    private Set<User> favoriteUsers;

    @JsonIgnore
    @OneToMany(mappedBy = "quizBank", cascade = CascadeType.ALL)
    private List<ManageBank> manageBanks;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "upvote_banks",
        joinColumns = @JoinColumn(name = "bank_id", referencedColumnName = "quiz_bank_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    )
    private Set<User> upVoteUsers;


}
