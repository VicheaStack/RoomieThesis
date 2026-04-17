package org.roomrental.group.RoomieHub.service;

import jdk.dynalink.NamedOperation;
import org.roomrental.group.RoomieHub.entity.Message;
import org.roomrental.group.RoomieHub.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {

    Notification create(Notification message);
    void deleteById(Long id);
}
