package org.roomrental.group.RoomieHub.notification;

public record NotificationRequestDTO(
        Long userId,
        String type,
        String title,
        String message,
        String data,
        String actionUrl
) {
}