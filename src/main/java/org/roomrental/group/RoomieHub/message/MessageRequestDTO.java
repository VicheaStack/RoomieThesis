package org.roomrental.group.RoomieHub.message;

public record MessageRequestDTO(
        Long bookingId,
        Long senderId,
        String messageText,
        String attachmentUrl
) {
}