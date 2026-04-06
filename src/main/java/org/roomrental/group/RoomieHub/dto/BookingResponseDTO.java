package org.roomrental.group.RoomieHub.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookingResponseDTO(
        Long bookingId,
        Long roomId,
        Long renterId,
        LocalDate startDate,
        LocalDate endDate,
        Integer totalNights,
        Double pricePerNight,
        Double totalAmount,
        String status,
        String specialRequests,
        String cancellationReason,
        LocalDateTime cancelledAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long paymentId,
        Long reviewId
) {
}