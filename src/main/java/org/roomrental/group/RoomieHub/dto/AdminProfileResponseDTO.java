package org.roomrental.group.RoomieHub.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AdminProfileResponseDTO(
        Long adminId,
        Long userId,
        String username,
        List<String> permissions,
        LocalDateTime lastLogin
) {
}