package org.roomrental.group.RoomieHub.amenity;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AmenityMapper {

    AmenityResponseDTO toDTO(Amenity amenity);

    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "amenityId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Amenity toEntity(AmenityRequestDTO amenityRequestDTO);
}
