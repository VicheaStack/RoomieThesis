package org.roomrental.group.RoomieHub.room;

import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.exception.AppException;
import org.roomrental.group.RoomieHub.user.User;
import org.roomrental.group.RoomieHub.user.UserRole;
import org.roomrental.group.RoomieHub.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public RoomServiceImpl(RoomRepository roomRepository,
                           UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    // RoomServiceImpl.java
    @Override
    public Room create(Room room, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found with id: " + ownerId));

        if (owner.getRole() != UserRole.OWNER) {
            throw AppException.of("User with id " + ownerId + " is not an owner. Only owners can create rooms.");
        }

        room.setOwner(owner);

        if (roomRepository.existsByOwnerAndTitle(owner, room.getTitle())) {
            throw AppException.of("Room already exists with this title for owner: " + room.getTitle());
        }

        Room saved = roomRepository.save(room);
        log.info("Room created: {}", saved);
        return saved;
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
            throw AppException.of("Room already booking: " + id);
        }
        log.info("Room already delete");
        roomRepository.deleteById(id);
    }
}
