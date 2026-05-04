package org.roomrental.group.RoomieHub.ownerProfile;

import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.exception.AppException;
import org.roomrental.group.RoomieHub.user.User;
import org.roomrental.group.RoomieHub.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class OwnerProfileServiceImpl implements OwnerProfileService {

    private final OwnerProfileRepository ownerProfileRepository;
    private final UserRepository userRepository;

    public OwnerProfileServiceImpl(OwnerProfileRepository ownerProfileRepository,
                                   UserRepository userRepository) {
        this.ownerProfileRepository = ownerProfileRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public OwnerProfile create(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        if (ownerProfileRepository.existsById(userId)) {
            throw AppException.of("OwnerProfile already exists for user: " + userId);
        }

        OwnerProfile profile = new OwnerProfile();
        profile.setUser(user);  // ← THIS IS THE KEY FIX
        profile.setTotalListings(0);
        profile.setRatingCount(0);
        profile.setTotalRating(0.0);
        return ownerProfileRepository.save(profile);
    }

    @Override
    @Transactional
    public OwnerProfile addRate(Long userId, double newRating) {
        OwnerProfile owner = ownerProfileRepository.findById(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found: " + userId));
                    OwnerProfile newProfile = new OwnerProfile();
                    newProfile.setUser(user);
                    newProfile.setTotalListings(0);
                    newProfile.setRatingCount(0);
                    newProfile.setTotalRating(0.0);
                    return ownerProfileRepository.save(newProfile);
                });

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
    @Transactional(readOnly = true)
    public OwnerProfile findById(Long userId) {
        return ownerProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("OwnerProfile not found for user: " + userId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OwnerProfile> findAll(Pageable pageable) {
        return ownerProfileRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void deleteById(Long userId) {
        if (!ownerProfileRepository.existsById(userId)) {
            throw AppException.of("OwnerProfile not found");
        }
        ownerProfileRepository.deleteById(userId);
    }
}