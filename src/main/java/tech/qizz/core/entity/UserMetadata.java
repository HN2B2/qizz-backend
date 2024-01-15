package tech.qizz.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_metadata")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_metadata_id")
    private long userMetadataId;

    @Lob
    @Column(name = "`key`",columnDefinition = "TEXT")
    private String key;

    @Lob
    @Column(name = "`value`",columnDefinition = "TEXT")
    private String value;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;
}
