package org.roomrental.group.RoomieHub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.roomrental.group.RoomieHub.dto.MessageResponseDTO;
import org.roomrental.group.RoomieHub.entity.Message;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "sender.userId", target = "senderId")
    MessageResponseDTO toDTO(Message message);
}