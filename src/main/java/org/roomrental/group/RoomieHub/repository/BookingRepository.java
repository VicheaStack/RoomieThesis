package org.roomrental.group.RoomieHub.repository;

import org.roomrental.group.RoomieHub.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
