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
@Table(name = "quiz_banks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizBank {

    @Lob
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "quiz_publicable")
    private Boolean quizPublicable;

    @Column(name = "public_editable")
    private Boolean publicEditable;


    @JsonIgnore
    @OneToMany(mappedBy = "quizBank", cascade = CascadeType.ALL)
    private List<Quiz> quizzess;

    @JsonIgnore
    @OneToMany(mappedBy = "quizBank", cascade = CascadeType.ALL)
    private List<Question> questions;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private User createdBy;

    @ManyToOne(optional = false)
    @JoinColumn(name = "modified_by", referencedColumnName = "user_id")
    private User modifiedBy;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "quiz_sub_categories",
        joinColumns = @JoinColumn(name = "bank_id", referencedColumnName = "quiz_bank_id"),
        inverseJoinColumns = @JoinColumn(name = "sub_category_id", referencedColumnName = "sub_category_id")
    )
    private Set<SubCategory> subCategories;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "favorite_banks",
        joinColumns = @JoinColumn(name = "bank_id", referencedColumnName = "quiz_bank_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    )
    private Set<User> users;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "manage_banks",
        joinColumns = @JoinColumn(name = "bank_id", referencedColumnName = "quiz_bank_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    )
    private Set<User> manageUsers;
}
