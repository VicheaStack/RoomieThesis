package org.roomrental.group.RoomieHub.favorite;

public record FavoriteRequestDTO(
        Long renterId,
        Long roomId
) {
}