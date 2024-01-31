package tech.qizz.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "question_histories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_history_id")
    private long questionHistoryId;

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

    @Column(name = "answer_metadata", nullable = false)
    private String answerMetadata;

    @Column(name = "score", nullable = false)
    private long score;

    @Temporal(TemporalType.TIME)
    @Column(name = "answer_time", nullable = false)
    private Date answerTime;

    @ManyToOne
    @JoinColumn(name = "quiz_joined_user_id", referencedColumnName = "quiz_joined_user_id")
    private QuizJoinedUser quizJoinedUser;

    @ManyToOne
    @JoinColumn(name = "quiz_question_id", referencedColumnName = "quiz_question_id")
    private QuizQuestion quizQuestion;
}
