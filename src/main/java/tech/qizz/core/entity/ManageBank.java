package tech.qizz.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "manage_banks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManageBank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "manage_bank_id")
    private long manageBankId;

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

    @Column(name = "editable")
    private Boolean editable;

}
