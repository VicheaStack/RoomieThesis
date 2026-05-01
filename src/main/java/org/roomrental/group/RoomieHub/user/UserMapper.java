package org.roomrental.group.RoomieHub.user;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Entity → DTO
    @Mapping(target = "role", expression = "java(mapRoleToString(user.getRole()))")
    UserResponseDTO toDTO(User user);

    // DTO → Entity
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "isVerified", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "ownerProfile", ignore = true)
    @Mapping(target = "adminProfile", ignore = true)
    @Mapping(target = "ownedRooms", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "sentMessages", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "favoriteRooms", ignore = true)
    @Mapping(target = "receivedMessages", ignore = true)
    User toEntity(UserRequestDTO dto);

    // Helper method
    default String mapRoleToString(UserRole role) {
        return role != null ? role.name() : null;
    }
}