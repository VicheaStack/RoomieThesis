package org.roomrental.group.RoomieHub.room;

import java.util.List;

public record RoomRequestDTO(
        Long ownerId,
        String title,
        String description,
        Double pricePerNight,
        String location,
        Double latitude,
        Double longitude,
        String roomType,
        Integer sizeSqft,
        Integer maxOccupancy,
        Boolean hasPrivateBathroom,
        Boolean isFurnished,
        Boolean isVerified,
        List<Long> amenityIds
) {
}