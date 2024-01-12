package tech.qizz.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "favorite_banks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteBank {

    @Lob
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "favorite_bank_id")
    private long favoriteBankId;

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


    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;
}
