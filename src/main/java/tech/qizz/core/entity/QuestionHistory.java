package tech.qizz.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "question_histories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
