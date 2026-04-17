package org.roomrental.group.RoomieHub.service;

import org.roomrental.group.RoomieHub.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface BookingService {

    Booking create(Booking booking);
    Booking update(Booking booking, Long id);
    Booking findById(Long id);
    Page<Booking> findAll(Pageable pageable);
    void deleteById(Long id);

}
