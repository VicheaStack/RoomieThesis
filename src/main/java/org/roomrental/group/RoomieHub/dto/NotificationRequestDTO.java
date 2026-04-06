package org.roomrental.group.RoomieHub.dto;

public record NotificationRequestDTO(
        Long userId,
        String type,
        String title,
        String message,
        String data,
        String actionUrl
) {
}