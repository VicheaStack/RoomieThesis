package org.roomrental.group.RoomieHub.repository;

import org.roomrental.group.RoomieHub.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
