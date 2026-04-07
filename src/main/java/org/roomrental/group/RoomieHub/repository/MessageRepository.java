package org.roomrental.group.RoomieHub.repository;

import org.roomrental.group.RoomieHub.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
