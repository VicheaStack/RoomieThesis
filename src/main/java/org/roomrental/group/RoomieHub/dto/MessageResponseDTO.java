package org.roomrental.group.RoomieHub.dto;

import java.time.LocalDateTime;

public record MessageResponseDTO(
        Long messageId,
        Long senderId,
        String messageText,
        LocalDateTime createdAt
) {
}