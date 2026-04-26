package org.roomrental.group.RoomieHub.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.roomrental.group.RoomieHub.dto.FavoriteRequestDTO;
import org.roomrental.group.RoomieHub.dto.FavoriteResponseDTO;
import org.roomrental.group.RoomieHub.entity.Favorite;

@Mapper(componentModel = "spring")
public interface FavroiteMapper {

    @Mapping(source = "room.roomId", target = "roomId")
    @Mapping(source = "renter.userId", target = "renterId")
    FavoriteResponseDTO toDTO(Favorite favorite);

    @Mapping(target = "room", ignore = true)
    @Mapping(target = "renter", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Favorite toEntity(FavoriteRequestDTO favoriteRequestDTO);
}
