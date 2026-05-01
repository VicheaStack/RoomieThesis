package org.roomrental.group.RoomieHub.photos;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhotoMapper {

    @Mapping(source = "room.roomId", target = "roomId")
    PhotoResponseDTO toDTO(Photo photo);

    @Mapping(target = "photoId", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "uploadedAt", ignore = true)
    Photo toEntity(PhotoRequestDTO photoRequestDTO);
}