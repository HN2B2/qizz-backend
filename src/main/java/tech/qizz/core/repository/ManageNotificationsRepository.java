package tech.qizz.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.qizz.core.entity.Notification;
import tech.qizz.core.entity.constant.NotificationTargetType;

@Repository
public interface ManageNotificationsRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.title LIKE CONCAT('%', :keyword, '%') OR n.content LIKE CONCAT('%', :keyword, '%') AND"
            + "(:target IS NULL OR n.targetType = :target)")
    Page<Notification> findNotificationsByKeywordAndTarget(
            @Param("keyword") String keyword,
            @Param("target")NotificationTargetType target,
            Pageable pageable);
    boolean existsByTitle(String title);

boolean existsByTargetType(NotificationTargetType targetType);
}
