package tech.qizz.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_notifications")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserNotification {

    @Lob
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_notification_id")
    private long userNotificationId;

    @Column(name = "checked")
    private Boolean checked;

    @ManyToOne
    @JoinColumn(name = "receiver_id",referencedColumnName = "user_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "sender_id",referencedColumnName = "user_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "notification_id",referencedColumnName = "notification_id")
    private Notification notification;


}
