package org.roomrental.group.RoomieHub.repository;

import org.roomrental.group.RoomieHub.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
