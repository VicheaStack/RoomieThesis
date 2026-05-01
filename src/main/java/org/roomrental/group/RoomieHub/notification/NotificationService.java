package org.roomrental.group.RoomieHub.notification;

import org.springframework.stereotype.Service;

@Service
public interface NotificationService {

    Notification create(Notification message);
    void deleteById(Long id);
}
