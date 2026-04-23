package org.roomrental.group.RoomieHub.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.entity.Notification;
import org.roomrental.group.RoomieHub.repository.NotificationRepository;
import org.roomrental.group.RoomieHub.service.NotificationService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification create(Notification message) {
        log.info("NotificationServiceImpl create");
        Notification save = notificationRepository.save(message);
        return save;
    }

    @Override
    public void deleteById(Long id) {

        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Notification Not Found");
        }
        notificationRepository.deleteById(id);
        log.info("NotificationServiceImpl delete");
    }
}
