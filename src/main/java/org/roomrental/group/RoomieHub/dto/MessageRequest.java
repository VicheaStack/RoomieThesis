package org.roomrental.group.RoomieHub.dto;

public record MessageRequest(
        Long senderId,
        String text
) {}