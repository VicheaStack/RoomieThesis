package org.roomrental.group.RoomieHub.favorite;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
