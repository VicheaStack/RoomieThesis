package org.roomrental.group.RoomieHub.room;

import java.time.LocalDateTime;
import java.util.List;

public record RoomResponseDTO(
        Long roomId,
        Long ownerId,
        String title,
        String description,
        Double pricePerNight,
        String location,
        Double latitude,
        Double longitude,
        String roomType,
        String status,
        Integer sizeSqft,
        Integer maxOccupancy,
        Boolean hasPrivateBathroom,
        Boolean isFurnished,
        Boolean isVerified,
        Integer totalViews,
        Integer totalBookings,
        Double averageRating,
        List<Long> amenityIds,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}