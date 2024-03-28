package tech.qizz.core.module.manageNotifications;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.Notification;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.constant.NotificationTargetType;
import tech.qizz.core.exception.ConflictException;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.module.manageNotifications.dto.CreateNotificationRequest;
import tech.qizz.core.module.manageNotifications.dto.GetAllNotificationsResponse;
import tech.qizz.core.module.manageNotifications.dto.NotificationResponse;
import tech.qizz.core.module.manageNotifications.dto.UpdateNotificationRequest;
import tech.qizz.core.repository.UserRepository;
import tech.qizz.core.repository.ManageNotificationsRepository;

@Service
@RequiredArgsConstructor
public class ManageNotificationsServiceImpl implements ManageNotificationsService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private  final ManageNotificationsRepository manageNotificationsRepository;
    @Override
    public GetAllNotificationsResponse getAllNotifications(
            Integer page,
            Integer limit,
            String keyword,
            NotificationTargetType target,
            String order,
            String sort) {
        Sort sortType = sort.equalsIgnoreCase("asc") ? Sort.by(order) : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page - 1, limit, sortType);

        Page<Notification> notifications = manageNotificationsRepository.findNotificationsByKeywordAndTarget(
                keyword,
                target,
                pageable);
        return GetAllNotificationsResponse.of(notifications);
    }

    @Override
    public NotificationResponse getNotificationById(Long id) {
        Notification notification = manageNotificationsRepository.findById(id).orElseThrow(() -> new NotFoundException("Notification not found"));
        return NotificationResponse.of(notification);
    }

    @Override
    public NotificationResponse createNotification(Long userId, CreateNotificationRequest body) {
        boolean exists = manageNotificationsRepository.existsByTitle(body.getTitle());
        if (exists) {
            throw new ConflictException("Title already exists");
        }
        User createrBy = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Notification notification = Notification.builder()
            .title(body.getTitle())
            .content(body.getContent())
            .targetType(body.getTargetType())
            .createdBy(createrBy)
            .build();

        return NotificationResponse.of(manageNotificationsRepository.save(notification));
    }

    @Override
    public NotificationResponse updateNotification(Long id, UpdateNotificationRequest body) {
        Notification notification = manageNotificationsRepository.findById(id).orElseThrow(() -> new NotFoundException("Notification not found"));
        modelMapper.map(body, notification);
        return NotificationResponse.of(manageNotificationsRepository.save(notification));
    }
}
