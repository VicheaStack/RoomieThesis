package org.roomrental.group.RoomieHub.repository;

import org.roomrental.group.RoomieHub.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
