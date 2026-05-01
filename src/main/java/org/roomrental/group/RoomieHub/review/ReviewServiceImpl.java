package org.roomrental.group.RoomieHub.review;

import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.booking.Booking;
import org.roomrental.group.RoomieHub.room.Room;
import org.roomrental.group.RoomieHub.user.User;
import org.roomrental.group.RoomieHub.booking.BookingRepository;
import org.roomrental.group.RoomieHub.room.RoomRepository;
import org.roomrental.group.RoomieHub.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             UserRepository userRepository,
                             RoomRepository roomRepository,
                             BookingRepository bookingRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Review create(Review review, Long renterId, Long roomId, Long bookingId) {
        User renter = userRepository.findById(renterId)
                .orElseThrow(() -> new RuntimeException("Renter not found with id: " + renterId));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        review.setRenter(renter);
        review.setRoom(room);
        review.setBooking(booking);

        Review saved = reviewRepository.save(review);
        log.info("Review created: id={}, room={}, renter={}, rating={}",
                saved.getReviewId(), roomId, renterId, saved.getRating());
        return saved;
    }

    @Override
    public Review update(Review review, Long id) {
        Review existing = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));

        if (review.getTitle() != null) existing.setTitle(review.getTitle());
        if (review.getComment() != null) existing.setComment(review.getComment());
        if (review.getRating() != 0) existing.setRating(review.getRating());
        if (review.getOwnerResponse() != null) existing.setOwnerResponse(review.getOwnerResponse());
        if (review.getIsVerified() != null) existing.setIsVerified(review.getIsVerified());

        Review saved = reviewRepository.save(existing);
        log.info("Review updated: {}", saved.getReviewId());
        return saved;
    }

    @Transactional(readOnly = true)
    @Override
    public Review findById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Review> findAll(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));

        // Clear the reference from Booking to avoid cascade issue
        if (review.getBooking() != null) {
            review.getBooking().setReview(null);
        }

        // Clear other relationships
        review.setRoom(null);
        review.setRenter(null);
        review.setBooking(null);

        reviewRepository.delete(review);
        log.info("Review deleted: {}", id);
    }
}