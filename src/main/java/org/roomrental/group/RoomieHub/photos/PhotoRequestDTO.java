package org.roomrental.group.RoomieHub.photos;

public record PhotoRequestDTO(
        Long roomId,
        String photoUrl,
        String caption,
        Boolean isPrimary,
        Integer displayOrder
) {
}