package org.roomrental.group.RoomieHub.dto;

public record ReviewRequestDTO(
        Long roomId,
        Long renterId,
        Long bookingId,
        Integer rating,
        String title,
        String comment
) {
}