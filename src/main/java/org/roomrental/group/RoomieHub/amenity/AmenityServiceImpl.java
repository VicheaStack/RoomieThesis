package org.roomrental.group.RoomieHub.amenity;

import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.exception.AppException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class AmenityServiceImpl implements AmenityService {

    private final AmenityRepository amenityRepository;

    public AmenityServiceImpl(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    @Override
    public Amenity create(Amenity amenity) {
        Amenity save = amenityRepository.save(amenity);
        log.info("Amenity created with id:{}",save.getAmenityId());
        return save;
    }

    @Transactional
    @Override
    public Amenity update(Amenity amenity, Long id) {
        Amenity room = amenityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("amenity not found with id:" + id));

        room.setName(amenity.getName());
        room.setDescription(amenity.getDescription());
        room.setCategory(amenity.getCategory());
        room.setIconClass(amenity.getIconClass());
        room.setIsActive(amenity.getIsActive());

        Amenity save = amenityRepository.save(room);
        log.info("Amenity updated with id:{}",save.getAmenityId());
        return save;
    }

    @Transactional(readOnly = true)
    @Override
    public Amenity findById(Long id) {
        Amenity amenity = amenityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("amenity not found with id:" + id));
        log.info("Amenity found with id:{}",amenity.getAmenityId());
        return amenity;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Amenity> findAll(Pageable pageable) {
        log.info("Fetching all amenity  with pagination: page {}, size {}",
                pageable.getPageSize(), pageable.getPageSize());
        return amenityRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if(!amenityRepository.existsById(id)){
            throw AppException.of("amenity not found with id:" + id);
        }
        amenityRepository.deleteById(id);
    }
}
