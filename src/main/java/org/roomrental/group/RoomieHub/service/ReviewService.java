package org.roomrental.group.RoomieHub.service;

import org.roomrental.group.RoomieHub.entity.Review;
import org.springframework.stereotype.Service;


@Service
public interface ReviewService {

    Review create(Review review);
    Review update(Long id, Review review);
}
