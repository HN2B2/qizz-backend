package tech.qizz.core.entity;

import jakarta.persistence.*;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "favorite_banks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_bank_id")
    private long favoriteBankId;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bank_id",referencedColumnName = "quiz_bank_id")
    private QuizBank quizBank;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;

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
