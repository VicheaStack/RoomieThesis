package org.roomrental.group.RoomieHub.ownerProfile;

import lombok.extern.slf4j.Slf4j;
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
    @Transactional
    public OwnerProfile addRate(Long userId, double newRating) {
        OwnerProfile owner = ownerProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("OwnerProfile not found for user: " + userId));

        owner.setRatingCount(owner.getRatingCount() + 1);
        owner.setTotalRating(owner.getTotalRating() + newRating);

        log.info("Updated rating for user {}. New rating count: {}", userId, owner.getRatingCount());
        return ownerProfileRepository.save(owner);
    }

    @Override
    @Transactional
    public OwnerProfile update(OwnerProfile ownerProfile, Long userId) {
        return ownerProfileRepository.findById(userId)
                .map(existing -> {
                    existing.setTotalListings(ownerProfile.getTotalListings());
                    return ownerProfileRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("OwnerProfile not found for user: " + userId));
    }

    @Override
    public OwnerProfile findById(Long userId) {
        return ownerProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("OwnerProfile not found for user: " + userId));
    }

    @Override
    public Page<OwnerProfile> findAll(Pageable pageable) {
        return ownerProfileRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void deleteById(Long userId) {
        if (!ownerProfileRepository.existsById(userId)) {
            throw new RuntimeException("OwnerProfile not found");
        }
        ownerProfileRepository.deleteById(userId);
    }
}
