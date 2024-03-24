package tech.qizz.core.module.manageNotifications;


import tech.qizz.core.entity.constant.NotificationTargetType;
import tech.qizz.core.module.manageNotifications.dto.CreateNotificationRequest;
import tech.qizz.core.module.manageNotifications.dto.GetAllNotificationsResponse;
import tech.qizz.core.module.manageNotifications.dto.NotificationResponse;
import tech.qizz.core.module.manageNotifications.dto.UpdateNotificationRequest;

public interface ManageNotificationsService {

    public GetAllNotificationsResponse getAllNotifications(
            Integer page,
            Integer limit,
            String keyword,
            NotificationTargetType target,
            String order,
            String sort);

    public NotificationResponse getNotificationById(Long id);

    public NotificationResponse createNotification(Long userId, CreateNotificationRequest body);

    public NotificationResponse updateNotification(Long id, UpdateNotificationRequest body);
}
