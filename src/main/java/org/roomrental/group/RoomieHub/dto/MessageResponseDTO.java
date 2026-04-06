package org.roomrental.group.RoomieHub.dto;

import java.time.LocalDateTime;

public record MessageResponseDTO(
        Long messageId,
        Long bookingId,
        Long senderId,
        String messageText,
        String attachmentUrl,
        Boolean isRead,
        LocalDateTime readAt,
        LocalDateTime createdAt
) {
}