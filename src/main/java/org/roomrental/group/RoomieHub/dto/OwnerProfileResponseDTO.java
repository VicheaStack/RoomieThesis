package org.roomrental.group.RoomieHub.dto;

public record OwnerProfileResponseDTO(
        Long userId,
        Integer totalListings,
        Integer ratingCount,
        Double totalRating
) {
}