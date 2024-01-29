package tech.qizz.core.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

import lombok.*;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "sub_categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_category_id")
    private long subCategoryId;

    @Column(name = "name", nullable = false)
    @Nationalized
    private String name;

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

    @Lob
    @Column(name = "description", nullable = false)
    @Nationalized
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @OneToMany(mappedBy = "subCategory",cascade = CascadeType.ALL)
    private List<QuizSubCategory> quizSubCategories;
}
