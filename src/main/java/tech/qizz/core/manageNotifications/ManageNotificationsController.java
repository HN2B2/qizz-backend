package tech.qizz.core.manageNotifications;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tech.qizz.core.annotation.RequestUser;
import tech.qizz.core.entity.User;
import tech.qizz.core.entity.constant.NotificationTargetType;
import tech.qizz.core.exception.BadRequestException;
import tech.qizz.core.manageNotifications.dto.CreateNotificationRequest;
import tech.qizz.core.manageNotifications.dto.GetAllNotificationsResponse;
import tech.qizz.core.manageNotifications.dto.NotificationResponse;
import tech.qizz.core.manageNotifications.dto.UpdateNotificationRequest;

@RestController
@RequestMapping("/notifications")
@CrossOrigin
@RequiredArgsConstructor
public class ManageNotificationsController {
    private final ManageNotificationsService manageNotificationsService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    public ResponseEntity<GetAllNotificationsResponse> getAllNotifications(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false) String target,
            @RequestParam(required = false, defaultValue = "id") String order,
            @RequestParam(required = false, defaultValue = "desc") String sort

    ){
        NotificationTargetType targetType = target == null ? null : NotificationTargetType.validateNotificationTargetType(target);

        GetAllNotificationsResponse notification = manageNotificationsService.getAllNotifications(
                page,
                limit,
                keyword,
                targetType,
                order,
                sort
        );

        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER','STAFF', 'ADMIN')")
    public ResponseEntity<NotificationResponse> getNotificationById(
            @PathVariable Long id
    ){
        NotificationResponse notification = manageNotificationsService.getNotificationById(id);
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<NotificationResponse> createNotification(
            @Valid @RequestBody CreateNotificationRequest body, @RequestUser User user, BindingResult result
    ){
        NotificationResponse notification = manageNotificationsService.createNotification(user.getUserId(),body);
        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<NotificationResponse> updateNotification(
            @PathVariable Long id, @Valid @RequestBody UpdateNotificationRequest body, BindingResult result
    ){
        if (result.hasErrors() || body == null) {
            throw new BadRequestException("Invalid request");
        }
        NotificationResponse notification = manageNotificationsService.updateNotification(id, body);
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }
}
