package org.roomrental.group.RoomieHub.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.entity.OwnerProfile;
import org.roomrental.group.RoomieHub.repository.OwnerProfileRepository;
import org.roomrental.group.RoomieHub.service.OwnerProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class OwnerProfileServiceImpl implements OwnerProfileService {

    private final OwnerProfileRepository ownerProfileRepository;

    public OwnerProfileServiceImpl(OwnerProfileRepository ownerProfileRepository) {
        this.ownerProfileRepository = ownerProfileRepository;
    }

    @Override
    public OwnerProfile addRate(Long ownerId, double newRating) {
        OwnerProfile owner = ownerProfileRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("OwnerProfile not found"));

        owner.setTotalListings(owner.getTotalListings() + 1);
        owner.setTotalRating(owner.getTotalRating() + newRating);

        OwnerProfile save = ownerProfileRepository.save(owner);
        log.info("Saved OwnerProfile {}", save);
        return save;
    }

    @Transactional
    @Override
    public OwnerProfile update(OwnerProfile ownerProfile, Long id) {
        OwnerProfile ownerProfileNotFound = ownerProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OwnerProfile not found"));

        ownerProfileNotFound.setTotalListings(ownerProfile.getTotalListings());

        OwnerProfile save = ownerProfileRepository.save(ownerProfileNotFound);
        log.info("OwnerProfile updated: {}", save);
        return save;
    }

    @Transactional(readOnly = true)
    @Override
    public OwnerProfile findById(Long id) {
        return ownerProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OwnerProfile not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<OwnerProfile> findAll(Pageable pageable) {
        log.info("Finding all OwnerProfiles {}, {} ",
                pageable.getPageNumber(), pageable.getPageSize());
        return ownerProfileRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        if(!ownerProfileRepository.existsById(id)) {
            throw new RuntimeException("OwnerProfile not found");
        }
        log.info("Deleting OwnerProfile {}", id);
        ownerProfileRepository.deleteById(id);
    }
}
