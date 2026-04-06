package org.roomrental.group.RoomieHub.dto;

public record FavoriteRequestDTO(
        Long renterId,
        Long roomId
) {
}