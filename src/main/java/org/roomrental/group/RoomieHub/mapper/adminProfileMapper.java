package org.roomrental.group.RoomieHub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.roomrental.group.RoomieHub.dto.AdminProfileResponseDTO;
import org.roomrental.group.RoomieHub.entity.AdminProfile;

@Mapper(componentModel = "spring")
public interface adminProfileMapper {

    @Mapping(source = "user.userId", target = "userId")
    AdminProfileResponseDTO toDTO(AdminProfile adminProfile);

    @Mapping(target = "user", ignore = true)
    AdminProfile toEntity(AdminProfileResponseDTO adminProfileResponseDTO);
}
