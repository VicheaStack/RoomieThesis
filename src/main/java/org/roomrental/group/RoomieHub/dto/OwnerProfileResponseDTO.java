package org.roomrental.group.RoomieHub.dto;

public record OwnerProfileResponseDTO(
        Long ownerId,
        Long userId,
        Integer totalListings,
        Double averageRating
) {
}