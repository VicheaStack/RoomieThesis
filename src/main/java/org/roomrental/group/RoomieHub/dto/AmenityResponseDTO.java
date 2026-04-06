package org.roomrental.group.RoomieHub.dto;

import java.time.LocalDateTime;

public record AmenityResponseDTO(
        Long amenityId,
        String name,
        String description,
        String iconClass,
        String category,
        Boolean isActive,
        LocalDateTime createdAt
) {
}