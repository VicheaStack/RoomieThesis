package org.roomrental.group.RoomieHub.message;

public record MessageRequest(
        Long senderId,
        String text
) {}