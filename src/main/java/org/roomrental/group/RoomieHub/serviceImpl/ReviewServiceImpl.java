package org.roomrental.group.RoomieHub.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.entity.Review;
import org.roomrental.group.RoomieHub.repository.ReviewRepository;
import org.roomrental.group.RoomieHub.service.ReviewService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review create(Review review) {
        Review save = reviewRepository.save(review);
        log.info("Review created: {}", save);
        return save;
    }

    @Override
    public Review update(Review review, Long id) {
        Review feedBack = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find review with id: " + id));

        feedBack.setTitle(review.getTitle());
        feedBack.setComment(review.getComment());
        feedBack.setRating(review.getRating());
        feedBack.setOwnerResponse(review.getOwnerResponse());
        feedBack.setIsVerified(review.getIsVerified());
        feedBack.setFlagReason(review.getFlagReason());
        feedBack.setHelpfulCount(review.getHelpfulCount());

        Review save = reviewRepository.save(feedBack);
        log.info("Review updated: {}", save);
        return save;
    }
}
