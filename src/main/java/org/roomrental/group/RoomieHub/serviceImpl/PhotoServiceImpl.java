package org.roomrental.group.RoomieHub.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.entity.Photo;
import org.roomrental.group.RoomieHub.entity.Room;
import org.roomrental.group.RoomieHub.repository.PhotoRepository;
import org.roomrental.group.RoomieHub.repository.RoomRepository;
import org.roomrental.group.RoomieHub.service.PhotoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final RoomRepository roomRepository;

    @Override
    public Photo create(Photo photo, Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));
        photo.setRoom(room);
        Photo saved = photoRepository.save(photo);
        log.info("Photo created with id: {}", saved.getPhotoId());
        return saved;
    }

    @Override
    public Photo update(Photo photo, Long id) {
        Photo existing = photoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Photo not found with id: " + id));

        if (photo.getPhotoUrl() != null) existing.setPhotoUrl(photo.getPhotoUrl());
        if (photo.getCaption() != null) existing.setCaption(photo.getCaption());
        if (photo.getIsPrimary() != null) existing.setIsPrimary(photo.getIsPrimary());
        if (photo.getDisplayOrder() != null) existing.setDisplayOrder(photo.getDisplayOrder());

        Photo updated = photoRepository.save(existing);
        log.info("Photo updated with id: {}", updated.getPhotoId());
        return updated;
    }

    @Override
    public void deleteById(Long id) {
        if (!photoRepository.existsById(id)) {
            throw new RuntimeException("Photo not found with id: " + id);
        }
        photoRepository.deleteById(id);
        log.info("Photo deleted with id: {}", id);
    }
}