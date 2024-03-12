package tech.qizz.core.manageNotifications;


import tech.qizz.core.entity.constant.NotificationTargetType;
import tech.qizz.core.manageNotifications.dto.CreateNotificationRequest;
import tech.qizz.core.manageNotifications.dto.GetAllNotificationsResponse;
import tech.qizz.core.manageNotifications.dto.NotificationResponse;
import tech.qizz.core.manageNotifications.dto.UpdateNotificationRequest;

public interface ManageNotificationsService {

    public GetAllNotificationsResponse getAllNotifications(Integer page, Integer limit, String keyword, NotificationTargetType target, String order, String sort);

    public NotificationResponse getNotificationById(Long id);

    public NotificationResponse createNotification(Long userId, CreateNotificationRequest body);

    public NotificationResponse updateNotification(Long id, UpdateNotificationRequest body);
}
