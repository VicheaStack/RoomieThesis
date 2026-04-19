package org.roomrental.group.RoomieHub.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.entity.OwnerProfile;
import org.roomrental.group.RoomieHub.repository.OwnerProfileRepository;
import org.roomrental.group.RoomieHub.service.OwnerProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
public class OwnerProfileServiceImpl implements OwnerProfileService {

    private final OwnerProfileRepository ownerProfileRepository;

    public OwnerProfileServiceImpl(OwnerProfileRepository ownerProfileRepository) {
        this.ownerProfileRepository = ownerProfileRepository;
    }

    @Override
    public OwnerProfile create(OwnerProfile ownerProfile) {
        OwnerProfile save = ownerProfileRepository.save(ownerProfile);
        log.info("OwnerProfile created: {}", save);
        return save;
    }

    @Override
    public OwnerProfile update(OwnerProfile ownerProfile, Long id) {
        return null;
    }

    @Override
    public OwnerProfile findById(Long id) {
        return null;
    }

    @Override
    public Page<OwnerProfile> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
