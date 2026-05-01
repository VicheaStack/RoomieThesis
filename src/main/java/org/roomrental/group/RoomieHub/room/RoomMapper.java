package org.roomrental.group.RoomieHub.room;

import org.mapstruct.*;
import org.roomrental.group.RoomieHub.amenity.Amenity;   // import if not already

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(source = "owner.userId", target = "ownerId")
    @Mapping(target = "status", expression = "java(mapStatusToString(room.getStatus()))")
    //@Mapping(source = "amenities", target = "amenityIds", qualifiedByName = "mapAmenitiesToIds")
    RoomResponseDTO toDTO(Room room);

    @Mapping(target = "roomId", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    @Mapping(target = "totalViews", ignore = true)
    @Mapping(target = "totalBookings", ignore = true)
    @Mapping(target = "averageRating", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Room toEntity(RoomRequestDTO dto);

    default String mapStatusToString(RoomStatus status) {
        return status != null ? status.name() : null;
    }

    @Named("mapAmenitiesToIds")
    default List<Long> mapAmenitiesToIds(List<Amenity> amenities) {
        if (amenities == null) {
            return List.of();
        }
        return amenities.stream()
                .map(Amenity::getAmenityId)
                .collect(Collectors.toList());
    }
}