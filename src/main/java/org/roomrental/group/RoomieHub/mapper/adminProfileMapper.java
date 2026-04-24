package org.roomrental.group.RoomieHub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.roomrental.group.RoomieHub.dto.AdminProfileRequestDTO;
import org.roomrental.group.RoomieHub.dto.AdminProfileResponseDTO;
import org.roomrental.group.RoomieHub.entity.AdminProfile;

@Mapper(componentModel = "spring")
public interface AdminProfileMapper {

    @Mapping(source = "user.userId", target = "userId")
    AdminProfileResponseDTO toDTO(AdminProfile adminProfile);

    @Mapping(target = "adminId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    AdminProfile toEntity(AdminProfileRequestDTO adminProfileRequestDTO);
}
