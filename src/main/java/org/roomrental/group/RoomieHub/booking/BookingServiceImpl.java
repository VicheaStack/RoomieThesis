package org.roomrental.group.RoomieHub.booking;

import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.room.Room;
import org.roomrental.group.RoomieHub.user.User;
import org.roomrental.group.RoomieHub.user.UserRole;
import org.roomrental.group.RoomieHub.room.RoomRepository;
import org.roomrental.group.RoomieHub.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              RoomRepository roomRepository,
                              UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Booking create(Booking booking, Long roomId, Long renterId) {

        // 1. Validate renter exists and is a TENANT
        User renter = userRepository.findById(renterId)
                .orElseThrow(() -> new RuntimeException("Renter not found with id: " + renterId));

        if (renter.getRole() != UserRole.RENTER) {
            throw new RuntimeException("Only tenants can book rooms. User " + renterId + " is a " + renter.getRole());
        }

        // 2. Validate room exists and is AVAILABLE
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));

        if (!"AVAILABLE".equals(room.getStatus().name())) {
            throw new RuntimeException("Room " + roomId + " is not available. Status: " + room.getStatus());
        }

        // 3. Validate dates
        if (booking.getEndDate().isBefore(booking.getStartDate())) {
            throw new RuntimeException("End date must be after start date");
        }
        if (booking.getStartDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Start date cannot be in the past");
        }

        // 4. Calculate nights and total amount
        long nights = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
        double pricePerNight = room.getPricePerNight();
        double totalAmount = nights * pricePerNight;

        // 5. Set all required fields
        booking.setRenter(renter);
        booking.setRoom(room);
        booking.setPricePerNight(pricePerNight);
        booking.setTotalNights((int) nights);
        booking.setTotalAmount(totalAmount);
        booking.setStatus(BookingStatus.PENDING);

        Booking saved = bookingRepository.save(booking);
        log.info("Booking created: id={}, room={}, renter={}, {} nights, total=${}",
                saved.getBookingId(), roomId, renterId, nights, totalAmount);
        return saved;
    }

    @Override
    public Booking update(Booking booking, Long id) {
        Booking update = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        if (booking.getStartDate() != null) {
            update.setStartDate(booking.getStartDate());
        }
        if (booking.getEndDate() != null) {
            update.setEndDate(booking.getEndDate());
        }
        if (booking.getSpecialRequests() != null) {
            update.setSpecialRequests(booking.getSpecialRequests());
        }
        if (booking.getStatus() != null) {
            update.setStatus(booking.getStatus());
        }

        Room room = update.getRoom();
        long nights = ChronoUnit.DAYS.between(update.getStartDate(), update.getEndDate());
        update.setPricePerNight(room.getPricePerNight());       // ← FROM ROOM
        update.setTotalNights((int) nights);                     // ← CALCULATED
        update.setTotalAmount(nights * room.getPricePerNight()); // ← CALCULATED

        Booking saved = bookingRepository.save(update);
        log.info("Booking updated: id={}, {} nights, total=${}",
                saved.getBookingId(), nights, saved.getTotalAmount());
        return saved;
    }

    @Transactional(readOnly = true)
    @Override
    public Booking findById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Booking> findAll(Pageable pageable) {
        log.debug("Fetching all bookings: page {}, size {}", pageable.getPageNumber(), pageable.getPageSize());
        return bookingRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
        log.info("Booking deleted: {}", id);
    }
}