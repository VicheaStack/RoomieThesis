package org.roomrental.group.RoomieHub.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AdminProfileRequestDTO(
        String username,
        List<String> permissions
) {
}
