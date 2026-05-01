package org.roomrental.group.RoomieHub.booking;

import java.time.LocalDate;

public record BookingRequestDTO(
        Long roomId,
        Long renterId,
        LocalDate startDate,
        LocalDate endDate,
        String specialRequests
) {
}