package tech.qizz.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

//    @JsonIgnore
//    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL)
//    private List<QuizSubCategory> quizSubCategories;
    @JsonIgnore
    @ManyToMany(mappedBy = "subCategories", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<QuizBank> intermediateQuizBanks;
}
