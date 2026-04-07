package org.roomrental.group.RoomieHub.service;

import org.roomrental.group.RoomieHub.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoomService {

    Room create(Room room);
    Room update(Long id, Room room);
    Optional<Room> findById(Long id);
    Page<Room> findAllRoom(Pageable pageable);
    void deleteById(Long id);
}
