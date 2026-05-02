package org.roomrental.group.RoomieHub.notification;

import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.user.User;
import org.roomrental.group.RoomieHub.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Notification create(Notification notification, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        notification.setUser(user);
        Notification saved = notificationRepository.save(notification);
        log.info("Notification created for user {}: {}", userId, saved.getNotificationId());
        return saved;
    }

    @Override
    public void deleteById(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Notification Not Found");
        }
        notificationRepository.deleteById(id);
        log.info("Notification deleted");
    }
}