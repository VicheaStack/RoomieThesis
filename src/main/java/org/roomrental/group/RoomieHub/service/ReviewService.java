package org.roomrental.group.RoomieHub.service;

import org.roomrental.group.RoomieHub.entity.Review;

public interface ReviewService {

    Review create(Review review);
    Review update(Long id, Review review);
}
