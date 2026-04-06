package org.roomrental.group.RoomieHub.dto;

public record SystemSettingRequestDTO(
        String settingKey,
        String settingValue,
        String dataType,
        String category,
        String description,
        Boolean isPublic,
        Long updatedBy
) {
}