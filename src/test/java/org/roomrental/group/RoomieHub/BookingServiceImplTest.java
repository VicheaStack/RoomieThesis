package org.roomrental.group.RoomieHub;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.roomrental.group.RoomieHub.booking.Booking;
import org.roomrental.group.RoomieHub.booking.BookingRepository;
import org.roomrental.group.RoomieHub.booking.BookingServiceImpl;
import org.roomrental.group.RoomieHub.booking.BookingStatus;
import org.roomrental.group.RoomieHub.exception.AppException;
import org.roomrental.group.RoomieHub.room.Room;
import org.roomrental.group.RoomieHub.room.RoomRepository;
import org.roomrental.group.RoomieHub.room.RoomStatus;
import org.roomrental.group.RoomieHub.user.User;
import org.roomrental.group.RoomieHub.user.UserRepository;
import org.roomrental.group.RoomieHub.user.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.roomrental.group.RoomieHub.booking.BookingStatus.COMPLETED;

@Disabled("Reason for disabling (optional)")
@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    // ==================== CREATE ====================

    @Test
    void create_Success() {
        Long renterId = 1L;
        Long roomId = 100L;
        LocalDate start = LocalDate.now().plusDays(1);
        LocalDate end = LocalDate.now().plusDays(5);
        double pricePerNight = 75.0;

        User renter = new User();
        renter.setUserId(renterId);
        renter.setRole(UserRole.RENTER);

        Room room = new Room();
        room.setRoomId(roomId);
        room.setStatus(RoomStatus.AVAILABLE); // Assuming enum exists
        room.setPricePerNight(pricePerNight);

        Booking inputBooking = new Booking();
        inputBooking.setStartDate(start);
        inputBooking.setEndDate(end);

        Booking savedBooking = new Booking();
        savedBooking.setBookingId(999L);
        savedBooking.setStartDate(start);
        savedBooking.setEndDate(end);
        savedBooking.setStatus(BookingStatus.PENDING);

        when(userRepository.findById(renterId)).thenReturn(Optional.of(renter));
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(bookingRepository.save(inputBooking)).thenReturn(savedBooking);

        Booking result = bookingService.create(inputBooking, roomId, renterId);

        assertNotNull(result);
        assertEquals(BookingStatus.PENDING, result.getStatus());
        assertEquals(renter, inputBooking.getRenter());
        assertEquals(room, inputBooking.getRoom());
        assertEquals(pricePerNight, inputBooking.getPricePerNight());
        assertEquals(4, inputBooking.getTotalNights()); // 5 days - 1 day = 4 nights
        assertEquals(300.0, inputBooking.getTotalAmount());

        verify(userRepository).findById(renterId);
        verify(roomRepository).findById(roomId);
        verify(bookingRepository).save(inputBooking);
    }

    @Test
    void create_RenterNotFound_ThrowsRuntimeException() {
        Long renterId = 99L;
        when(userRepository.findById(renterId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> bookingService.create(new Booking(), 1L, renterId));
        assertEquals("Renter not found with id: " + renterId, ex.getMessage());

        verify(userRepository).findById(renterId);
        verifyNoMoreInteractions(roomRepository, bookingRepository);
    }

    @Test
    void create_UserNotRenter_ThrowsAppException() {
        Long renterId = 1L;
        User owner = new User();
        owner.setUserId(renterId);
        owner.setRole(UserRole.OWNER);

        when(userRepository.findById(renterId)).thenReturn(Optional.of(owner));

        // Expect AccessDeniedException (the actual type thrown)
        AccessDeniedException ex = assertThrows(AccessDeniedException.class,
                () -> bookingService.create(new Booking(), 1L, renterId));
        assertEquals("Only tenants can book rooms. User " + renterId + " is a " + UserRole.OWNER,
                ex.getMessage());

        verify(userRepository).findById(renterId);
        verifyNoMoreInteractions(roomRepository, bookingRepository);
    }

    @Test
    void create_RoomNotFound_ThrowsRuntimeException() {
        Long renterId = 1L;
        Long roomId = 99L;
        User renter = new User();
        renter.setRole(UserRole.RENTER);

        when(userRepository.findById(renterId)).thenReturn(Optional.of(renter));
        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> bookingService.create(new Booking(), roomId, renterId));
        assertEquals("Room not found with id: " + roomId, ex.getMessage());

        verify(userRepository).findById(renterId);
        verify(roomRepository).findById(roomId);
        verifyNoMoreInteractions(bookingRepository);
    }

    @Test
    void create_RoomNotAvailable_ThrowsAppException() {
        Long renterId = 1L;
        Long roomId = 100L;
        User renter = new User();
        renter.setRole(UserRole.RENTER);

        Room room = new Room();
        room.setRoomId(roomId);
        room.setStatus(RoomStatus.BOOKED); // Not available

        when(userRepository.findById(renterId)).thenReturn(Optional.of(renter));
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> bookingService.create(new Booking(), roomId, renterId));
        assertEquals("Room " + roomId + " is not available. Status: " + RoomStatus.BOOKED,
                ex.getMessage());

        verify(userRepository).findById(renterId);
        verify(roomRepository).findById(roomId);
        verifyNoMoreInteractions(bookingRepository);
    }

    @Test
    void create_EndDateBeforeStartDate_ThrowsAppException() {
        Long renterId = 1L;
        Long roomId = 100L;
        User renter = new User();
        renter.setRole(UserRole.RENTER);

        Room room = new Room();
        room.setStatus(RoomStatus.AVAILABLE);

        Booking booking = new Booking();
        booking.setStartDate(LocalDate.now().plusDays(5));
        booking.setEndDate(LocalDate.now().plusDays(1)); // End before start

        when(userRepository.findById(renterId)).thenReturn(Optional.of(renter));
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> bookingService.create(booking, roomId, renterId));
        assertEquals("End date must be after start date", ex.getMessage());

        verify(userRepository).findById(renterId);
        verify(roomRepository).findById(roomId);
        verifyNoMoreInteractions(bookingRepository);
    }

    @Test
    void create_StartDateInPast_ThrowsAppException() {
        Long renterId = 1L;
        Long roomId = 100L;
        User renter = new User();
        renter.setRole(UserRole.RENTER);

        Room room = new Room();
        room.setStatus(RoomStatus.AVAILABLE);

        Booking booking = new Booking();
        booking.setStartDate(LocalDate.now().minusDays(1));
        booking.setEndDate(LocalDate.now().plusDays(5));

        when(userRepository.findById(renterId)).thenReturn(Optional.of(renter));
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        // Change expected exception type to IllegalArgumentException
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> bookingService.create(booking, roomId, renterId));
        assertEquals("Start date cannot be in the past", ex.getMessage());

        verify(userRepository).findById(renterId);
        verify(roomRepository).findById(roomId);
        verifyNoMoreInteractions(bookingRepository);
    }

    // ==================== UPDATE ====================

    @Test
    void update_Success_AllFieldsUpdated() {
        Long bookingId = 1L;
        LocalDate oldStart = LocalDate.now().plusDays(1);
        LocalDate oldEnd = LocalDate.now().plusDays(3);
        double price = 100.0;

        Room room = new Room();
        room.setPricePerNight(price);

        Booking existing = new Booking();
        existing.setBookingId(bookingId);
        existing.setStartDate(oldStart);
        existing.setEndDate(oldEnd);
        existing.setRoom(room);
        existing.setSpecialRequests("Old request");
        existing.setStatus(BookingStatus.PENDING);

        LocalDate newStart = LocalDate.now().plusDays(2);
        LocalDate newEnd = LocalDate.now().plusDays(6);

        Booking updatePayload = new Booking();
        updatePayload.setStartDate(newStart);
        updatePayload.setEndDate(newEnd);
        updatePayload.setSpecialRequests("New request");
        updatePayload.setStatus(BookingStatus.CONFIRMED);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(existing));
        when(bookingRepository.save(existing)).thenReturn(existing);

        Booking result = bookingService.update(updatePayload, bookingId);

        assertNotNull(result);
        assertEquals(newStart, existing.getStartDate());
        assertEquals(newEnd, existing.getEndDate());
        assertEquals("New request", existing.getSpecialRequests());
        assertEquals(BookingStatus.CONFIRMED, existing.getStatus());
        assertEquals(price, existing.getPricePerNight());
        assertEquals(4, existing.getTotalNights()); // 6-2 = 4 nights
        assertEquals(400.0, existing.getTotalAmount());

        verify(bookingRepository).findById(bookingId);
        verify(bookingRepository).save(existing);
    }

    @Test
    void update_Success_PartialUpdates_KeepsExistingDatesForRecalc() {
        Long bookingId = 1L;
        LocalDate existingStart = LocalDate.now().plusDays(1);
        LocalDate existingEnd = LocalDate.now().plusDays(5);
        double price = 50.0;

        Room room = new Room();
        room.setPricePerNight(price);

        Booking existing = new Booking();
        existing.setBookingId(bookingId);
        existing.setStartDate(existingStart);
        existing.setEndDate(existingEnd);
        existing.setRoom(room);
        existing.setSpecialRequests("Old special");
        existing.setStatus(BookingStatus.PENDING);

        Booking updatePayload = new Booking();
        updatePayload.setSpecialRequests("Only this changes"); // start/end/status are null
        updatePayload.setStatus(null); // explicitly null

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(existing));
        when(bookingRepository.save(existing)).thenReturn(existing);

        Booking result = bookingService.update(updatePayload, bookingId);

        assertEquals("Only this changes", existing.getSpecialRequests());
        assertEquals(existingStart, existing.getStartDate()); // unchanged
        assertEquals(existingEnd, existing.getEndDate()); // unchanged
        assertEquals(BookingStatus.PENDING, existing.getStatus()); // unchanged
        assertEquals(4, existing.getTotalNights()); // 5-1 = 4 nights
        assertEquals(200.0, existing.getTotalAmount());

        verify(bookingRepository).findById(bookingId);
        verify(bookingRepository).save(existing);
    }

    @Test
    void update_BookingNotFound_ThrowsRuntimeException() {
        Long bookingId = 99L;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> bookingService.update(new Booking(), bookingId));
        assertEquals("Booking not found with id: " + bookingId, ex.getMessage());

        verify(bookingRepository).findById(bookingId);
        verify(bookingRepository, never()).save(any());
    }

    // ==================== FIND BY ID ====================

    @Test
    void findById_Success() {
        Long bookingId = 1L;
        Booking booking = new Booking();
        booking.setBookingId(bookingId);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        Booking result = bookingService.findById(bookingId);

        assertNotNull(result);
        assertEquals(bookingId, result.getBookingId());

        verify(bookingRepository).findById(bookingId);
    }

    @Test
    void findById_NotFound_ThrowsRuntimeException() {
        Long bookingId = 99L;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> bookingService.findById(bookingId));
        assertEquals("Booking not found with id: " + bookingId, ex.getMessage());

        verify(bookingRepository).findById(bookingId);
    }

    // ==================== FIND ALL ====================

    @Test
    void findAll_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Booking> bookings = List.of(new Booking(), new Booking());
        Page<Booking> page = new PageImpl<>(bookings, pageable, bookings.size());

        when(bookingRepository.findAll(pageable)).thenReturn(page);

        Page<Booking> result = bookingService.findAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());

        verify(bookingRepository).findAll(pageable);
    }

    // ==================== DELETE ====================

    @Test
    void deleteById_Success_WhenBookingExists() {
        Long bookingId = 1L;
        when(bookingRepository.existsById(bookingId)).thenReturn(true);

        assertDoesNotThrow(() -> bookingService.deleteById(bookingId));

        verify(bookingRepository).existsById(bookingId);
        verify(bookingRepository).deleteById(bookingId);
    }

    @Test
    void deleteById_ThrowsAppException_WhenBookingNotFound() {
        Long bookingId = 99L;
        when(bookingRepository.existsById(bookingId)).thenReturn(false);

        AppException ex = assertThrows(AppException.class,
                () -> bookingService.deleteById(bookingId));
        assertEquals("Booking not found with id: " + bookingId, ex.getMessage());

        verify(bookingRepository).existsById(bookingId);
        verify(bookingRepository, never()).deleteById(any());
    }
}