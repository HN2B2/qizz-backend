package tech.qizz.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quiz_settings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizSetting {


    @Lob
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "quiz_setting_id")
    private long quizSettingId;

    @Lob
    @Column(name = "`key`",columnDefinition = "TEXT")
    private String key;

    @Lob
    @Column(name = "`value`",columnDefinition = "TEXT")
    private String value;

    @ManyToOne
    @JoinColumn(name = "quiz_id",referencedColumnName = "quiz_id")
    private Quiz quiz;
}
