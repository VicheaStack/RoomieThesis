package org.roomrental.group.RoomieHub.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    Review create(Review review, Long renterId, Long roomId, Long bookingId);
    Review update(Review review, Long id);
    Review findById(Long id);
    Page<Review> findAll(Pageable pageable);
    void deleteById(Long id);
}