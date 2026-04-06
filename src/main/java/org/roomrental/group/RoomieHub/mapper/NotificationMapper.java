package org.roomrental.group.RoomieHub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.roomrental.group.RoomieHub.dto.NotificationResponseDTO;
import org.roomrental.group.RoomieHub.entity.Notification;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(source = "user.userId", target = "userId")
    NotificationResponseDTO toDTO(Notification notification);
}