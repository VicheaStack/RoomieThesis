package org.roomrental.group.RoomieHub.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.entity.Room;
import org.roomrental.group.RoomieHub.repository.RoomRepository;
import org.roomrental.group.RoomieHub.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

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
