package tech.qizz.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "questions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Lob
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "question_id")
    private long questionId;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Lob
    @Column(name = "point", nullable = false)
    private long point;

    @Temporal(TemporalType.TIME)
    @Column(name = "duration",nullable = false)
    private Date durataion;

    @Column(name = "type", nullable = false)
    private String type;

    @Lob
    @Column(name = "answers_metadata", nullable = false)
    private String answersMetadata;

    @Lob
    @Column(name = "correct_answers_metadata", nullable = false)
    private String correctAnswersMetadata;

    @Lob
    @Column(name = "explain_answer", nullable = false)
    private String explainAnswer;

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

    @Column(name = "disabled")
    private Boolean disabled;

    @ManyToOne
    @JoinColumn(name = "bank_id",referencedColumnName = "quiz_bank_id")
    private QuizBank quizBank;

    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuizQuestion> quizQuestions;

//    @ManyToMany(mappedBy = "questions",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    private Set<Quiz> intermediateQuizs;
}
