package org.roomrental.group.RoomieHub.room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoomService {

    // RoomServiceImpl.java
    Room create(Room room, Long ownerId);

    Room update(Long id, Room room);
    Optional<Room> findById(Long id);
    Page<Room> findAllRoom(Pageable pageable);
    void deleteById(Long id);
}
