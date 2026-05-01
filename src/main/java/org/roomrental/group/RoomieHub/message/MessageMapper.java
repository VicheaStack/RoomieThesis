package org.roomrental.group.RoomieHub.message;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "sender.userId", target = "senderId")
    MessageResponseDTO toDTO(Message message);

    @Mapping(target = "messageId", ignore = true)
    @Mapping(target = "conversation", ignore = true)
    @Mapping(target = "sender", ignore = true)
    @Mapping(target = "isRead", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Message toEntity(MessageRequestDTO messageRequestDTO);
}