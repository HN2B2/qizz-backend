package tech.qizz.core.entity;

import jakarta.persistence.*;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quiz_sub_categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizSubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_sub_category_id")
    private long quizSubCategoryId;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sub_category_id",referencedColumnName = "sub_category_id")
    private SubCategory subCategory;

    @ManyToOne(optional = false)
    @JoinColumn (name = "bank_id",referencedColumnName = "quiz_bank_id")
    private QuizBank quizBank;
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        modifiedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = new Date();
    }

}
