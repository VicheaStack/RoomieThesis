package org.roomrental.group.RoomieHub.service;

import org.roomrental.group.RoomieHub.entity.Amenity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface AmenityService {

    Amenity create(Amenity amenity);
    Amenity update(Amenity amenity, Long id );
    Amenity findById(Long id);
    Page<Amenity> findAll(Pageable pageable);
    void deleteById(Long id);
}
