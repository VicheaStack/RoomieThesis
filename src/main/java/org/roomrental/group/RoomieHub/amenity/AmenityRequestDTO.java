package org.roomrental.group.RoomieHub.amenity;

public record AmenityRequestDTO(
        String name,
        String description,
        String iconClass,
        String category,
        Boolean isActive
) {
}