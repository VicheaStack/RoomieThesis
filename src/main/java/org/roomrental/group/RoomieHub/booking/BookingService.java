// BookingService.java
package org.roomrental.group.RoomieHub.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    Booking create(Booking booking, Long roomId, Long renterId);  // ← ADD PARAMETERS
    Booking update(Booking booking, Long id);
    Booking findById(Long id);
    Page<Booking> findAll(Pageable pageable);
    void deleteById(Long id);
}