package org.roomrental.group.RoomieHub.adminProfile;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminProfileMapper {

    @Mapping(source = "user.userId", target = "userId")
    AdminProfileResponseDTO toDTO(AdminProfile adminProfile);

    @Mapping(target = "adminId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    AdminProfile toEntity(AdminProfileRequestDTO adminProfileRequestDTO);
}
