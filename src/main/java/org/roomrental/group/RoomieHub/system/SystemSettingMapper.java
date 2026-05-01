package org.roomrental.group.RoomieHub.system;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.roomrental.group.RoomieHub.user.User;

@Mapper(componentModel = "spring")
public interface SystemSettingMapper {

    // Entity → DTO
    @Mapping(source = "updatedBy.userId", target = "updatedById")
    SystemSettingResponseDTO toDto(SystemSetting systemSetting);

    // DTO → Entity
    @Mapping(target = "settingId", ignore = true)
    @Mapping(source = "updatedById", target = "updatedBy.userId")
    @Mapping(target = "updatedAt", ignore = true)
    SystemSetting toEntity(SystemSettingRequestDTO dto);

    // Helper: Long → User
    default User map(Long userId) {
        if (userId == null) return null;

        User user = new User();
        user.setUserId(userId);
        return user;
    }

    // Helper: User → Long (optional but safe)
    default Long map(User user) {
        return user != null ? user.getUserId() : null;
    }
}