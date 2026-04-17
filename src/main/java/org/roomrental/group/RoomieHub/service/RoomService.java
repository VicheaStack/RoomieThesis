package org.roomrental.group.RoomieHub.service;

import org.roomrental.group.RoomieHub.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RoomService {

    Room create(Room room);
    Room update(Long id, Room room);
    Optional<Room> findById(Long id);
    Page<Room> findAllRoom(Pageable pageable);
    void deleteById(Long id);
}
