package org.roomrental.group.RoomieHub.dto;

import java.time.LocalDateTime;

public record NotificationResponseDTO(
        Long notificationId,
        Long userId,
        String type,
        String title,
        String message,
        String data,
        Boolean isRead,
        LocalDateTime readAt,
        String actionUrl,
        LocalDateTime createdAt
) {
}