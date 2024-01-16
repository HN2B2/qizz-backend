package tech.qizz.core.entity;

import jakarta.persistence.*;

import java.util.Date;
import lombok.*;

@Entity
@Table(name = "upvote_banks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpVoteBank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "upvote_bank_id")
    private long upvoteBankId;

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
