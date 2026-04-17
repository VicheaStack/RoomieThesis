package org.roomrental.group.RoomieHub.service;

import org.roomrental.group.RoomieHub.entity.Message;
import org.roomrental.group.RoomieHub.entity.OwnerProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface OwnerProfileService {

    OwnerProfile create(OwnerProfile ownerProfile);
    OwnerProfile update(OwnerProfile ownerProfile, Long id);
    OwnerProfile findById(Long id);
    Page<OwnerProfile> findAll(Pageable pageable);
    void deleteById(Long id);
}
