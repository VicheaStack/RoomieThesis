package org.roomrental.group.RoomieHub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.roomrental.group.RoomieHub.dto.PhotoRequestDTO;
import org.roomrental.group.RoomieHub.dto.PhotoResponseDTO;
import org.roomrental.group.RoomieHub.entity.Photo;

@Mapper(componentModel = "spring")
public interface PhotoMapper {

    @Mapping(source = "room.roomId", target = "roomId")
    PhotoResponseDTO toDTO(Photo photo);

    @Mapping(target = "photoId", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "uploadedAt", ignore = true)
    Photo toEntity(PhotoRequestDTO photoRequestDTO);
}