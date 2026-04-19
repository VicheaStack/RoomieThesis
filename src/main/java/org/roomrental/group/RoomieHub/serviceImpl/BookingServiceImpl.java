package org.roomrental.group.RoomieHub.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.roomrental.group.RoomieHub.entity.Booking;
import org.roomrental.group.RoomieHub.repository.BookingRepository;
import org.roomrental.group.RoomieHub.service.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking create(Booking booking) {
        log.info("create new booking for user ");
        return bookingRepository.save(booking);
    }

    @Transactional
    @Override
    public Booking update(Booking booking, Long id) {
        Booking update = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("can't find user record"));

        update.setStartDate(booking.getStartDate());
        update.setEndDate(booking.getEndDate());
        update.setPricePerNight(booking.getPricePerNight());
        update.setStatus(booking.getStatus());

        Booking save = bookingRepository.save(update);
        log.info("booking update successfully ");
        return save;
    }

    @Transactional(readOnly = true)
    @Override
    public Booking findById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found booking record"));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Booking> findAll(Pageable pageable) {
        log.debug("Fetching all bookings with pagination: page {}, size {}",
                pageable.getPageNumber(), pageable.getPageSize());
        return bookingRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        if(!bookingRepository.existsById(id)){
            throw new RuntimeException("Booking not exist ");
        }
        bookingRepository.deleteById(id);
        log.info("Booking cancel successfully");
    }
}
