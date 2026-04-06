package org.roomrental.group.RoomieHub.dto;

import java.time.LocalDate;

public record BookingRequestDTO(
        Long roomId,
        Long renterId,
        LocalDate startDate,
        LocalDate endDate,
        String specialRequests
) {
}