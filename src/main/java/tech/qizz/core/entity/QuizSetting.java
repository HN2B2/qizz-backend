package tech.qizz.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_setting_id")
    private long quizSettingId;

    @Lob
    @Column(name = "`key`", columnDefinition = "TEXT")
    private String key;

    @Lob
    @Column(name = "`value`", columnDefinition = "TEXT")
    private String value;

    @ManyToOne
    @JoinColumn(name = "quiz_id", referencedColumnName = "quiz_id")
    private Quiz quiz;
}
