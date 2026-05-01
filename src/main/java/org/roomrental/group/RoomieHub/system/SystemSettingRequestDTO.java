package org.roomrental.group.RoomieHub.system;

public record SystemSettingRequestDTO(
        String settingKey,
        String settingValue,
        String dataType,
        String category,
        String description,
        Boolean isPublic,
        Long updatedById
) {}