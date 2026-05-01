package org.roomrental.group.RoomieHub.ownerProfile;

public record OwnerProfileRequestDTO(
        Long userId,
        Integer totalListings
) {}