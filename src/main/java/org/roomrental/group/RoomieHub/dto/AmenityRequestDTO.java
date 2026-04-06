package org.roomrental.group.RoomieHub.dto;

public record AmenityRequestDTO(
        String name,
        String description,
        String iconClass,
        String category,
        Boolean isActive
) {
}