package org.roomrental.group.RoomieHub.favorite;

import java.time.LocalDateTime;

public record FavoriteResponseDTO(
        Long id,
        Long renterId,
        Long roomId,
        LocalDateTime createdAt
) {
}