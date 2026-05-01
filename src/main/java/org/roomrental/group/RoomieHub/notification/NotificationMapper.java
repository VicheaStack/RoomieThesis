package org.roomrental.group.RoomieHub.notification;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(source = "user.userId", target = "userId")
    NotificationResponseDTO toDTO(Notification notification);

    @Mapping(target = "notificationId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "isRead", ignore = true)
    @Mapping(target = "readAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Notification toEntity(NotificationRequestDTO notificationRequestDTO);
}