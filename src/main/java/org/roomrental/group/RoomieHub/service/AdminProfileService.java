package org.roomrental.group.RoomieHub.service;

import org.roomrental.group.RoomieHub.entity.AdminProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface AdminProfileService {

    AdminProfile create(AdminProfile adminProfile);
    AdminProfile update(AdminProfile adminProfile, Long id);
    AdminProfile findById(Long id);
    Page<AdminProfile> findAll(Pageable pageable);
    void deleteById(Long id);
}
