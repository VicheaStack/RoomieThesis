package org.roomrental.group.RoomieHub;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.roomrental.group.RoomieHub.booking.Booking;
import org.roomrental.group.RoomieHub.review.Review;
import org.roomrental.group.RoomieHub.review.ReviewRepository;
import org.roomrental.group.RoomieHub.review.ReviewServiceImpl;
import org.roomrental.group.RoomieHub.room.Room;
import org.roomrental.group.RoomieHub.user.User;
import org.roomrental.group.RoomieHub.booking.BookingRepository;
import org.roomrental.group.RoomieHub.room.RoomRepository;
import org.roomrental.group.RoomieHub.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Disabled("Reason for disabling (optional)")
@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    // ==================== CREATE ====================

    @Test
    void create_Success() {
        Long renterId = 1L;
        Long roomId = 100L;
        Long bookingId = 200L;

        User renter = new User();
        renter.setUserId(renterId);

        Room room = new Room();
        room.setRoomId(roomId);

        Booking booking = new Booking();
        booking.setBookingId(bookingId);

        Review inputReview = new Review();
        inputReview.setTitle("Great stay");
        inputReview.setComment("Wonderful experience");
        inputReview.setRating(5);

        Review savedReview = new Review();
        savedReview.setReviewId(10L);
        savedReview.setTitle("Great stay");
        savedReview.setRenter(renter);
        savedReview.setRoom(room);
        savedReview.setBooking(booking);
        savedReview.setRating(5);

        when(userRepository.findById(renterId)).thenReturn(Optional.of(renter));
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(reviewRepository.save(inputReview)).thenReturn(savedReview);

        Review result = reviewService.create(inputReview, renterId, roomId, bookingId);

        assertNotNull(result);
        assertEquals(10L, result.getReviewId());
        assertEquals("Great stay", result.getTitle());
        assertEquals(renter, result.getRenter());
        assertEquals(room, result.getRoom());
        assertEquals(booking, result.getBooking());
        assertEquals(5, result.getRating());

        verify(userRepository).findById(renterId);
        verify(roomRepository).findById(roomId);
        verify(bookingRepository).findById(bookingId);
        verify(reviewRepository).save(inputReview);
    }

    @Test
    void create_RenterNotFound_ThrowsRuntimeException() {
        Long renterId = 99L;
        when(userRepository.findById(renterId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reviewService.create(new Review(), renterId, 1L, 1L));
        assertEquals("Renter not found with id: " + renterId, ex.getMessage());

        verify(userRepository).findById(renterId);
        verifyNoMoreInteractions(roomRepository, bookingRepository, reviewRepository);
    }

    @Test
    void create_RoomNotFound_ThrowsRuntimeException() {
        Long renterId = 1L;
        Long roomId = 99L;
        User renter = new User();
        renter.setUserId(renterId);

        when(userRepository.findById(renterId)).thenReturn(Optional.of(renter));
        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reviewService.create(new Review(), renterId, roomId, 1L));
        assertEquals("Room not found with id: " + roomId, ex.getMessage());

        verify(userRepository).findById(renterId);
        verify(roomRepository).findById(roomId);
        verifyNoMoreInteractions(bookingRepository, reviewRepository);
    }

    @Test
    void create_BookingNotFound_ThrowsRuntimeException() {
        Long renterId = 1L;
        Long roomId = 1L;
        Long bookingId = 99L;
        User renter = new User();
        renter.setUserId(renterId);
        Room room = new Room();
        room.setRoomId(roomId);

        when(userRepository.findById(renterId)).thenReturn(Optional.of(renter));
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reviewService.create(new Review(), renterId, roomId, bookingId));
        assertEquals("Booking not found with id: " + bookingId, ex.getMessage());

        verify(userRepository).findById(renterId);
        verify(roomRepository).findById(roomId);
        verify(bookingRepository).findById(bookingId);
        verifyNoMoreInteractions(reviewRepository);
    }

    // ==================== UPDATE ====================

    @Test
    void update_Success_AllFieldsProvided() {
        Long reviewId = 1L;
        Review existing = new Review();
        existing.setReviewId(reviewId);
        existing.setTitle("Old title");
        existing.setComment("Old comment");
        existing.setRating(3);
        existing.setOwnerResponse(null);
        existing.setIsVerified(false);

        Review updatePayload = new Review();
        updatePayload.setTitle("New title");
        updatePayload.setComment("New comment");
        updatePayload.setRating(5);
        updatePayload.setOwnerResponse("Thank you!");
        updatePayload.setIsVerified(true);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existing));
        when(reviewRepository.save(existing)).thenReturn(existing);

        Review result = reviewService.update(updatePayload, reviewId);

        assertNotNull(result);
        assertEquals("New title", existing.getTitle());
        assertEquals("New comment", existing.getComment());
        assertEquals(5, existing.getRating());
        assertEquals("Thank you!", existing.getOwnerResponse());
        assertTrue(existing.getIsVerified());

        verify(reviewRepository).findById(reviewId);
        verify(reviewRepository).save(existing);
    }

    @Test
    void update_Success_OnlySomeFieldsProvided() {
        Long reviewId = 1L;
        Review existing = new Review();
        existing.setReviewId(reviewId);
        existing.setTitle("Old title");
        existing.setComment("Old comment");
        existing.setRating(3);
        existing.setOwnerResponse("Old response");
        existing.setIsVerified(false);

        Review updatePayload = new Review();
        // Only title and rating changed; comment left null, ownerResponse null, isVerified null
        updatePayload.setTitle("Updated title");
        updatePayload.setRating(4);
        // comment remains null, so it should NOT be updated
        // ownerResponse remains null, should NOT be updated
        // isVerified remains null, should NOT be updated

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existing));
        when(reviewRepository.save(existing)).thenReturn(existing);

        Review result = reviewService.update(updatePayload, reviewId);

        assertEquals("Updated title", existing.getTitle());
        assertEquals("Old comment", existing.getComment()); // unchanged
        assertEquals(4, existing.getRating());
        assertEquals("Old response", existing.getOwnerResponse()); // unchanged
        assertFalse(existing.getIsVerified()); // unchanged

        verify(reviewRepository).findById(reviewId);
        verify(reviewRepository).save(existing);
    }

    @Test
    void update_RatingZero_DoesNotUpdateRating() {
        // rating is 0 in payload, should be ignored because condition checks != 0
        Long reviewId = 1L;
        Review existing = new Review();
        existing.setRating(3);
        Review updatePayload = new Review();
        updatePayload.setRating(0);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existing));
        when(reviewRepository.save(existing)).thenReturn(existing);

        reviewService.update(updatePayload, reviewId);
        assertEquals(3, existing.getRating()); // unchanged

        verify(reviewRepository).save(existing);
    }

    @Test
    void update_ReviewNotFound_ThrowsRuntimeException() {
        Long reviewId = 99L;
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reviewService.update(new Review(), reviewId));
        assertEquals("Review not found with id: " + reviewId, ex.getMessage());

        verify(reviewRepository).findById(reviewId);
        verify(reviewRepository, never()).save(any());
    }

    // ==================== FIND BY ID ====================

    @Test
    void findById_Success() {
        Long reviewId = 1L;
        Review review = new Review();
        review.setReviewId(reviewId);
        review.setTitle("Awesome");

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        Review result = reviewService.findById(reviewId);

        assertNotNull(result);
        assertEquals(reviewId, result.getReviewId());
        assertEquals("Awesome", result.getTitle());

        verify(reviewRepository).findById(reviewId);
    }

    @Test
    void findById_NotFound_ThrowsRuntimeException() {
        Long reviewId = 99L;
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reviewService.findById(reviewId));
        assertEquals("Review not found with id: " + reviewId, ex.getMessage());

        verify(reviewRepository).findById(reviewId);
    }

    // ==================== FIND ALL ====================

    @Test
    void findAll_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Review> reviews = List.of(new Review(), new Review());
        Page<Review> page = new PageImpl<>(reviews, pageable, reviews.size());

        when(reviewRepository.findAll(pageable)).thenReturn(page);

        Page<Review> result = reviewService.findAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(pageable, result.getPageable());

        verify(reviewRepository).findAll(pageable);
    }

    // ==================== DELETE ====================

    @Test
    void deleteById_Success_BookingNotNull() {
        Long reviewId = 1L;

        // Create room first
        Room room = new Room();
        room.setRoomId(200L);   // use the correct setter (or setId)

        // Create user
        User renter = new User();
        renter.setUserId(300L); // adjust if setter is different

        // Create booking and set room
        Booking booking = new Booking();
        booking.setRoom(room);   // ✅ now room is defined

        // Create review and set associations
        Review review = new Review();
        review.setReviewId(reviewId);
        review.setBooking(booking);
        review.setRoom(room);
        review.setRenter(renter);

        // Bidirectional link
        booking.setReview(review);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        reviewService.deleteById(reviewId);

        // Verify relationships cleared
        assertNull(booking.getReview());
        assertNull(review.getBooking());
        assertNull(review.getRoom());
        assertNull(review.getRenter());

        verify(reviewRepository).findById(reviewId);
        verify(reviewRepository).delete(review);
    }

    @Test
    void deleteById_Success_BookingNull() {
        Long reviewId = 2L;
        Review review = new Review();
        review.setReviewId(reviewId);
        review.setBooking(null); // no associated booking
        review.setRoom(new Room());
        review.setRenter(new User());

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        reviewService.deleteById(reviewId);

        assertNull(review.getBooking()); // stays null
        assertNull(review.getRoom());
        assertNull(review.getRenter());

        verify(reviewRepository).findById(reviewId);
        verify(reviewRepository).delete(review);
        // Ensure bookingRepository not called at all (since we're not saving booking)
        verifyNoInteractions(bookingRepository);
    }

    @Test
    void deleteById_ReviewNotFound_ThrowsRuntimeException() {
        Long reviewId = 99L;
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reviewService.deleteById(reviewId));
        assertEquals("Review not found with id: " + reviewId, ex.getMessage());

        verify(reviewRepository).findById(reviewId);
        verify(reviewRepository, never()).delete(any());
    }

    // Additional edge case: delete where booking.getReview() is null (already cleared)
    // but our code checks booking != null before calling setReview(null), so it's safe.
}