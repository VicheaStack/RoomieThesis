package org.roomrental.group.RoomieHub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.roomrental.group.RoomieHub.dto.OwnerProfileResponseDTO;
import org.roomrental.group.RoomieHub.entity.OwnerProfile;

@Mapper(componentModel = "spring")
public interface OwnerProfileMapper {

    // Entity → DTO
    @Mapping(target = "userId", source = "ownerId")
    OwnerProfileResponseDTO toDto(OwnerProfile ownerProfile);

    @Mapping(target = "user", ignore = true)
    OwnerProfile toEntity(OwnerProfileResponseDTO dto);
}