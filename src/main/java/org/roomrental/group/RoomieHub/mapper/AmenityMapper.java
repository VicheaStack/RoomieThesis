package org.roomrental.group.RoomieHub.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.roomrental.group.RoomieHub.dto.AmenityRequestDTO;
import org.roomrental.group.RoomieHub.dto.AmenityResponseDTO;
import org.roomrental.group.RoomieHub.entity.Amenity;

@Mapper(componentModel = "spring")
public interface AmenityMapper {

    AmenityResponseDTO toDTO(Amenity amenity);

    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "amenityId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Amenity toEntity(AmenityRequestDTO amenityRequestDTO);
}
