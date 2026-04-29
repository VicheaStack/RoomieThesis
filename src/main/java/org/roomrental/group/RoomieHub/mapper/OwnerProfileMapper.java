package org.roomrental.group.RoomieHub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.roomrental.group.RoomieHub.dto.OwnerProfileRequestDTO;
import org.roomrental.group.RoomieHub.dto.OwnerProfileResponseDTO;
import org.roomrental.group.RoomieHub.entity.OwnerProfile;
import org.roomrental.group.RoomieHub.entity.User;

@Mapper(componentModel = "spring")
public interface OwnerProfileMapper {

    // ENTITY → DTO
    @Mapping(source = "user.userId", target = "userId")
    OwnerProfileResponseDTO toDto(OwnerProfile ownerProfile);

    // DTO → ENTITY
    @Mapping(target = "totalRating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    @Mapping(target = "user", source = "userId")
    OwnerProfile toEntity(OwnerProfileRequestDTO dto);

    // helper: Long → User
    default User map(Long userId) {
        if (userId == null) return null;

        User user = new User();
        user.setUserId(userId);
        return user;
    }

    // helper: User → Long
    default Long map(User user) {
        return user != null ? user.getUserId() : null;
    }
}