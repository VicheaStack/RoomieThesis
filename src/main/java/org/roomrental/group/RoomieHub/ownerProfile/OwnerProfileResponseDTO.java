package org.roomrental.group.RoomieHub.ownerProfile;

public record OwnerProfileResponseDTO(
        Long userId,
        Integer totalListings,
        Integer ratingCount,
        Double totalRating
) {
}