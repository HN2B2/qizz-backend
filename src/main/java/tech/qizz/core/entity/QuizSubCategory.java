package tech.qizz.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "quiz_sub_categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizSubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "quiz_sub_category_id")
    private long quizSubCategoryId;

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

}
