package org.roomrental.group.RoomieHub.dto;

public record MessageRequestDTO(
        Long bookingId,
        Long senderId,
        String messageText,
        String attachmentUrl
) {
}