package org.roomrental.group.RoomieHub.dto;

public record OwnerProfileRequestDTO(
        Long userId,
        Integer totalListings,
        Double averageRating
) {
}