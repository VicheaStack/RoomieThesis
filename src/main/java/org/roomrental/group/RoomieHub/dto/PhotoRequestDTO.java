package org.roomrental.group.RoomieHub.dto;

public record PhotoRequestDTO(
        Long roomId,
        String photoUrl,
        String caption,
        Boolean isPrimary,
        Integer displayOrder
) {
}