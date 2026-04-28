package org.roomrental.group.RoomieHub.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.entity.Photo;
import org.roomrental.group.RoomieHub.repository.PhotoRepository;
import org.roomrental.group.RoomieHub.service.PhotoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;

    @Override
    public Photo create(Photo photo) {
        Photo saved = photoRepository.save(photo);
        log.info("Photo created with id: {}", saved.getPhotoId());
        return saved;
    }

    @Override
    public Photo update(Photo photo, Long id) {
        Photo existing = photoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Photo not found with id: " + id));

        // Update only the fields that are allowed to change
        existing.setPhotoUrl(photo.getPhotoUrl());
        existing.setCaption(photo.getCaption());
        existing.setIsPrimary(photo.getIsPrimary());
        existing.setDisplayOrder(photo.getDisplayOrder());
        // room relationship should not be changed once set

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