package org.roomrental.group.RoomieHub.dto;

import java.time.LocalDateTime;

public record SystemSettingResponseDTO(
        Long settingId,
        String settingKey,
        String settingValue,
        String dataType,
        String category,
        String description,
        Boolean isPublic,
        LocalDateTime updatedAt,
        Long updatedById
) {}