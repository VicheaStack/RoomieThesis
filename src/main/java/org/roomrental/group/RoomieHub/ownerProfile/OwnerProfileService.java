package org.roomrental.group.RoomieHub.ownerProfile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface OwnerProfileService {

    // Updates owner metrics when a new rating is received
    OwnerProfile addRate(Long userId, double newRating);

    // Standard CRUD operations for profile management
    OwnerProfile update(OwnerProfile ownerProfile, Long userId);
    OwnerProfile findById(Long userId);
    Page<OwnerProfile> findAll(Pageable pageable);
    void deleteById(Long userId);
}
