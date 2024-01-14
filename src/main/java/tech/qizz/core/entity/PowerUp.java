package tech.qizz.core.entity;

import jakarta.persistence.*;
import jdk.jfr.Registered;
import lombok.*;

@Entity
@Table(name = "power_up")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PowerUp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "power_up_id")
    private long powerUpId;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Lob
    @Column(name = "image", nullable = false)
    private String image;
}
