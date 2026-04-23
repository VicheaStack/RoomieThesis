package org.roomrental.group.RoomieHub.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.entity.Room;
import org.roomrental.group.RoomieHub.repository.RoomRepository;
import org.roomrental.group.RoomieHub.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room create(Room room) {
        if(roomRepository.existsByOwnerAndTitle(room.getOwner(), room.getTitle())) {
            throw new RuntimeException("Room already booking: " + room.getRoomId());
        }
        return roomRepository.save(room);
    }

    @Transactional
    @Override
    public Room update(Long id, Room room) {
        Room check = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found: " + id));

        check.setTitle(room.getTitle());
        check.setDescription(room.getDescription());
        check.setPricePerNight(room.getPricePerNight());
        check.setLocation(room.getLocation());
        check.setSizeSqft(room.getSizeSqft());
        check.setMaxOccupancy(room.getMaxOccupancy());
        check.setHasPrivateBathroom(room.getHasPrivateBathroom());
        check.setIsFurnished(room.getIsFurnished());
        check.setIsVerified(room.getIsVerified());

        Room save = roomRepository.save(check);
        log.info("Room updated: {}", save);
        return save;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Room> findById(Long id) {
        Optional<Room> byId = roomRepository.findById(id);
        if(byId.isEmpty()) {
            throw  new RuntimeException("Room not found: " + id);
        }
        log.info("Room not found: {}", byId.get());
        return byId;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Room> findAllRoom(Pageable pageable) {
        log.info("Fetching all rooms by page: {}, {}", pageable.getPageNumber(), pageable.getPageSize());
        return roomRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if(roomRepository.existsById(id)) {
            throw new RuntimeException("Room already booking: " + id);
        }
        log.info("Room already delete");
        roomRepository.deleteById(id);
    }
}
