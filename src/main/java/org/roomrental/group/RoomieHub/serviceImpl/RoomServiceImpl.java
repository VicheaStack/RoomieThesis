package org.roomrental.group.RoomieHub.serviceImpl;

import org.roomrental.group.RoomieHub.entity.Room;
import org.roomrental.group.RoomieHub.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class RoomServiceImpl implements RoomService {
    @Override
    public Room create(Room room) {
        return null;
    }

    @Override
    public Room update(Long id, Room room) {
        return null;
    }

    @Override
    public Optional<Room> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Page<Room> findAllRoom(Pageable pageable) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
