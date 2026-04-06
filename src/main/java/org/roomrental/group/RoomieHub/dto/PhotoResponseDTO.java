package org.roomrental.group.RoomieHub.dto;

import java.time.LocalDateTime;

public record PhotoResponseDTO(
        Long photoId,
        Long roomId,
        String photoUrl,
        String caption,
        Boolean isPrimary,
        Integer displayOrder,
        LocalDateTime uploadedAt
) {
}